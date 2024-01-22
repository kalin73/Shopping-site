package com.example.shopping.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {
    private static final String EMAIL_VERIFICATION_TEXT = "Please click below to verify your new account:";
    private static final String PASSWORD_CHANGE_VERIFICATION = "To change your password please click the link below.";

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.verify.host}")
    private String host;

    private final static String PASSWORD_CHANGE_URL = "/auth/changePassword?token=";
    private final static String EMAIL_VERIFICATION_URL = "/auth/verify?token=";

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendConfirmationEmail(String name, String to, String token) {
        String url = host + PASSWORD_CHANGE_URL + token;
        createEmail(PASSWORD_CHANGE_VERIFICATION, "Changing password confirmation", url, name, to);
    }

    @Async
    public void sendAccountValidationEmail(String name, String to, String token) {
        String url = host + EMAIL_VERIFICATION_URL + token;
        createEmail(EMAIL_VERIFICATION_TEXT, "New User account verification.", url, name, to);
    }

    private void createEmail(String infoText, String subject, String url, String name, String to) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name, "url", url, "infoText", infoText));

            String text = templateEngine.process("accountValidationEmail", context);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);

            emailSender.send(message);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

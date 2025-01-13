package com.example.shopping.service;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.entity.LoginLog;
import com.example.shopping.repository.LoginLogRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginLogService {
    private final LoginLogRepository loginLogRepository;
    private final UserRepository userRepository;

    public LoginLogService(LoginLogRepository loginLogRepository, UserRepository userRepository) {
        this.loginLogRepository = loginLogRepository;
        this.userRepository = userRepository;
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void successfulLoginEventListener() {
        ApplicationUserDetails principal = (ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        this.userRepository.findByEmail(principal.getUsername())
                .ifPresent(user -> this.loginLogRepository.save(new LoginLog(LocalDateTime.now(), user)));
    }
}

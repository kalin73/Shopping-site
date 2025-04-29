package com.example.shopping.service;

import com.example.shopping.exceptions.ExpiredTokenException;
import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.dto.UserDto;
import com.example.shopping.model.dto.UserProfileDto;
import com.example.shopping.model.entity.ConfirmationEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ConfirmationRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationRepository confirmationRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationRepository = confirmationRepository;
        this.emailService = emailService;
    }

    public UserDto registerUser(RegisterFormDto userRegisterForm) {
        String email = userRegisterForm.getEmail();

        UserEntity user = new UserEntity(userRegisterForm.getFirstName(), userRegisterForm.getLastName(),
                email, passwordEncoder.encode(userRegisterForm.getPassword()),
                userRegisterForm.getPhoneNumber()).setEnabled(false);

        user = this.userRepository.save(user);

        ConfirmationEntity confirmation = new ConfirmationEntity(user);
        this.confirmationRepository.save(confirmation);

        this.emailService.sendAccountValidationEmail(userRegisterForm.getFirstName(), userRegisterForm.getEmail(), confirmation.getToken());

        return UserDto.mapToUserDto(user);

    }

    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email).orElse(null);

        if (userEntity != null) {
            return UserDto.mapToUserDto(userEntity);
        }

        return null;
    }

    public String verifyToken(String token) {
        ConfirmationEntity confirmation = this.confirmationRepository.findByToken(token);

        if (LocalDateTime.now().isAfter(confirmation.getExpire())) {
            throw new ExpiredTokenException("Verification link expired!");
        }

        return confirmation.getUser().getEmail();
    }

    public void changePassword(String email, String newPassword) {
        UserEntity user = this.userRepository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));

        this.userRepository.save(user);
    }

    public void sendConfirmationEmail(UserDto user) {
        UserEntity userEntity = this.userRepository.findByEmail(user.getEmail()).orElse(null);

        ConfirmationEntity confirmation = new ConfirmationEntity(userEntity);
        this.confirmationRepository.save(confirmation);

        this.emailService.sendConfirmationEmail(user.getFirstName(), user.getEmail(), confirmation.getToken());
    }

    public void sendVerificationEmail(String email) {
        Optional<UserEntity> user = this.userRepository.findByEmail(email);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            ConfirmationEntity confirmation = new ConfirmationEntity(userEntity);

            this.confirmationRepository.save(confirmation);

            this.emailService.sendAccountValidationEmail(userEntity.getFirstName(), userEntity.getEmail(), confirmation.getToken());
        }
    }

    @Transactional
    public UserProfileDto getUserProfile(String email) {
        return this.userRepository.findByEmail(email)
                .map(UserProfileDto::mapToUserProfileDto)
                .orElse(null);
    }

    @Transactional
    public void validateUser(String token) {
        this.userRepository.updateAccountStatus(true, verifyToken(token));
    }
}

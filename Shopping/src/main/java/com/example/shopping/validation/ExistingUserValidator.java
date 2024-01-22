package com.example.shopping.validation;

import com.example.shopping.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistingUserValidator implements ConstraintValidator<ValidateUniqueEmail, String> {
    private final UserRepository userRepository;

    public ExistingUserValidator(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !this.userRepository.existsByEmail(email);
    }
}

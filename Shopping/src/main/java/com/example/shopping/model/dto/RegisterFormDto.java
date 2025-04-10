package com.example.shopping.model.dto;

import com.example.shopping.validation.ValidatePhoneNumber;
import com.example.shopping.validation.ValidateUniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterFormDto {
    @Pattern(regexp = "[A-Za-z]{2,30}", message = "First name should be at least 2 letters long")
    private String firstName;

    @Pattern(regexp = "[A-Za-z]{2,30}", message = "Last name should be at least 2 letters long")
    private String lastName;

    @Email
    @ValidateUniqueEmail
    private String email;

    @NotBlank(message = "Passwords should not be empty")
    private String password;

    @ValidatePhoneNumber
    private String phoneNumber;

    public RegisterFormDto() {

    }

    public RegisterFormDto(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public RegisterFormDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterFormDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterFormDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterFormDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RegisterFormDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

}

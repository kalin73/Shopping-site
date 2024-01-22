package com.example.shopping.model.dto;

public class PasswordChangeDto {
    private String password;
    private String confirmPassword;

    public PasswordChangeDto() {

    }

    public String getPassword() {
        return password;
    }

    public PasswordChangeDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public PasswordChangeDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}

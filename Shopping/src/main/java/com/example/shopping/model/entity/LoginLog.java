package com.example.shopping.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_logs")
public class LoginLog extends BaseEntity {
    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public LoginLog setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public LoginLog setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}

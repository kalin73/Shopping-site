package com.example.shopping.model.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmations")
public class ConfirmationEntity extends BaseEntity{
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expire;

    @Column
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ConfirmationEntity() {

    }

    public ConfirmationEntity(UserEntity user) {
        this.user = user;
        this.created = LocalDateTime.now();
        this.expire = created.plusMinutes(3L);
        this.token = UUID.randomUUID().toString();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ConfirmationEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public ConfirmationEntity setExpire(LocalDateTime expire) {
        this.expire = expire;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ConfirmationEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public ConfirmationEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}

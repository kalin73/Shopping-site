package com.example.shopping.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "CVC_code")
    private String cvcCode;

    @Column(name = "expiration_date")
    private LocalDate expDate;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;

    public CreditCardEntity() {

    }

    public CreditCardEntity(String ownerName, String cardNumber, String cvcCode, LocalDate expDate, UserEntity user) {
        this.ownerName = ownerName;
        this.cardNumber = cardNumber;
        this.cvcCode = cvcCode;
        this.expDate = expDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public CreditCardEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public CreditCardEntity setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardEntity setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getCvcCode() {
        return cvcCode;
    }

    public CreditCardEntity setCvcCode(String cvcCode) {
        this.cvcCode = cvcCode;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CreditCardEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public CreditCardEntity setExpDate(LocalDate expDate) {
        this.expDate = expDate;
        return this;
    }
}

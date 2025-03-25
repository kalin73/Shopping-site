package com.example.shopping.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class CreditCardForm {
    @Pattern(regexp = "[a-zA-Z]{2,20}\\s[a-zA-Z]{2,20}", message = "Invalid name format")
    private String ownerName;

    @Pattern(regexp = "\\d{12}", message = "The card number should be exactly 12 digits")
    private String cardNumber;

    @Pattern(regexp = "\\d{3}", message = "The cvc code should be exactly 3 digits")
    private String cvcCode;

    private String month;

    @Min(2025)
    private int year;

    public String getOwnerName() {
        return ownerName;
    }

    public CreditCardForm setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardForm setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getCvcCode() {
        return cvcCode;
    }

    public CreditCardForm setCvcCode(String cvcCode) {
        this.cvcCode = cvcCode;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public CreditCardForm setMonth(String month) {
        this.month = month;
        return this;
    }

    public int getYear() {
        return year;
    }

    public CreditCardForm setYear(int year) {
        this.year = year;
        return this;
    }
}

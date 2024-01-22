package com.example.shopping.model.dto;

public class CreditCardForm {
    private String ownerName;

    private String cardNumber;

    private String cvcCode;

    private String month;

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

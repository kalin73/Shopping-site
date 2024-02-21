package com.example.shopping.model.dto;

public class OrderDto {
    private String fullName;
    private String phoneNumber;
    private String shippingAddress;
    private String paymentMethod;

    public OrderDto() {
    }

    public OrderDto(String fullName, String phoneNumber, String shippingAddress, String paymentMethod) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public String getFullName() {
        return fullName;
    }

    public OrderDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderDto setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public OrderDto setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }
}

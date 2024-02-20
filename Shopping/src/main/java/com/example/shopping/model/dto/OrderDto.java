package com.example.shopping.model.dto;

public class OrderDto {
    private String fullName;
    private String phone;
    private String shippingAddress;
    private String paymentMethod;

    public OrderDto() {
    }

    public OrderDto(String fullName, String phone, String shippingAddress, String paymentMethod) {
        this.fullName = fullName;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public OrderDto setPhone(String phone) {
        this.phone = phone;
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

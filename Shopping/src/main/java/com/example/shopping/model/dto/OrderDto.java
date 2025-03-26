package com.example.shopping.model.dto;

import com.example.shopping.validation.ValidatePhoneNumber;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderDto {
    @Pattern(regexp = "[A-Za-z]{3,20}\\s?[A-Za-z]{3,20}\\s?[A-Za-z]{3,20}")
    private String fullName;

    @ValidatePhoneNumber
    private String phoneNumber;

    @Size(min = 3, max = 100)
    private String shippingAddress;

    @NotNull
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

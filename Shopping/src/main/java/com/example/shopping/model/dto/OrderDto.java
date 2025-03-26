package com.example.shopping.model.dto;

import com.example.shopping.validation.ValidatePhoneNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderDto {
    @Pattern(regexp = "[A-Za-z]{2,20}\\s?[A-Za-z]{0,20}\\s?[A-Za-z]{0,20}", message = "Full name should be at least 2 letters long")
    private String fullName;

    @ValidatePhoneNumber
    private String phoneNumber;

    @Size(min = 3, max = 100, message = "Address should be between 3 and 100 symbols long")
    private String shippingAddress;

    @NotNull(message = "Please chose payment method")
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

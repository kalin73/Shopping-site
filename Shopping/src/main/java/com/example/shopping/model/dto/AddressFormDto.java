package com.example.shopping.model.dto;

import com.example.shopping.validation.ValidatePhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AddressFormDto {
    @Pattern(regexp = "[A-Za-z]{2,20}\\s?[A-Za-z]{0,20}\\s?[A-Za-z]{0,20}", message = "Full name should be at least 2 letters long")
    private String fullName;

    @ValidatePhoneNumber
    private String phoneNumber;

    @NotBlank(message = "Country should not be empty")
    private String country;

    @NotBlank(message = "City should not be empty")
    private String city;

    @NotBlank(message = "Address should not be empty")
    private String address;

    public AddressFormDto() {

    }

    public AddressFormDto(String fullName, String phoneNumber, String country, String city, String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public AddressFormDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AddressFormDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressFormDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressFormDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AddressFormDto setAddress(String address) {
        this.address = address;
        return this;
    }
}

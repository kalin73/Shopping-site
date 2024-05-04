package com.example.shopping.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressFormDto {
    @NotBlank
    private String fullName;

    @Size(min = 10, max = 10)
    private String phoneNumber;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
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

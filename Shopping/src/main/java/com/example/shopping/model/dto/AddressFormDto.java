package com.example.shopping.model.dto;

public class AddressFormDto {
    private String fullName;

    private String phoneNumber;

    private String country;

    private String city;

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

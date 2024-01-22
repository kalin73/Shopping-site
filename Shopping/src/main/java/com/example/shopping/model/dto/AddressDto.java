package com.example.shopping.model.dto;

import com.example.shopping.model.entity.AddressEntity;

public class AddressDto {
    private Long id;

    private String country;

    private String city;

    private String address;

    public AddressDto() {
    }

    public AddressDto(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public AddressDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AddressDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public static AddressDto mapToAddressDto(AddressEntity addressEntity) {
        AddressDto address = new AddressDto();
        address.setAddress(address.getAddress())
                .setCity(addressEntity.getCity())
                .setCountry(addressEntity.getCountry());

        return address;
    }
}

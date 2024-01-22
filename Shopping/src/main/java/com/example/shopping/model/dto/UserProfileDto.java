package com.example.shopping.model.dto;

import com.example.shopping.model.entity.UserEntity;

import java.util.List;

public class UserProfileDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private List<AddressDto> deliveryAddresses;

    public UserProfileDto() {
    }

    public UserProfileDto(String firstName, String lastName, String email, String phoneNumber, List<AddressDto> deliveryAddresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.deliveryAddresses = deliveryAddresses;
    }

    public Long getId() {
        return id;
    }

    public UserProfileDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserProfileDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<AddressDto> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public UserProfileDto setDeliveryAddresses(List<AddressDto> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
        return this;
    }


    public static UserProfileDto mapToUserProfileDto(UserEntity userEntity) {
        UserProfileDto user = new UserProfileDto();
        user.setEmail(userEntity.getEmail())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setPhoneNumber(userEntity.getPhoneNumber())
                .setDeliveryAddresses(userEntity.getDeliveryAddresses().stream().map(AddressDto::mapToAddressDto).toList());

        return user;
    }
}

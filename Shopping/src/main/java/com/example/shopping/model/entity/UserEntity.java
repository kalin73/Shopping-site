package com.example.shopping.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user")
    private List<AddressEntity> deliveryAddresses;

    @OneToMany(mappedBy = "user", targetEntity = LoginLog.class, fetch = FetchType.EAGER)
    private List<LoginLog> logs;

    public UserEntity() {
        orders = new ArrayList<>();
        deliveryAddresses = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public UserEntity(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public UserEntity setEnabled(Boolean enabled) {
        isEnabled = enabled;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public UserEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }

    public List<AddressEntity> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<AddressEntity> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public List<LoginLog> getLogs() {
        return logs;
    }

    public UserEntity setLogs(List<LoginLog> logs) {
        this.logs = logs;
        return this;
    }
}

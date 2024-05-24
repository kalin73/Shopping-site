package com.example.shopping.model.entity;

import com.example.shopping.model.enums.PaymentMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @OneToMany(mappedBy = "shoppingOrder")
    private List<OrderItemEntity> items;

    @Column
    private LocalDateTime date;

    @Column(name = "order_cost")
    private BigDecimal orderCost;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "phone_number")
    private String phoneNumber;

    public OrderEntity() {

    }

    public UserEntity getCustomer() {
        return customer;
    }

    public OrderEntity setCustomer(UserEntity customer) {
        this.customer = customer;
        return this;
    }

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(List<OrderItemEntity> items) {
        this.items = items;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderEntity setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public OrderEntity setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public OrderEntity setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}

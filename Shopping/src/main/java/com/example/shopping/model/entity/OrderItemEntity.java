package com.example.shopping.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "oreder_items")
public class OrderItemEntity extends BaseEntity {
    @ManyToOne
    private ProductEntity product;

    @JoinColumn(name = "shopping_order")
    @ManyToOne
    private OrderEntity shoppingOrder;

    public OrderItemEntity() {

    }

    public OrderItemEntity(ProductEntity product, OrderEntity shoppingOrder) {
        this.product = product;
        this.shoppingOrder = shoppingOrder;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public OrderItemEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public OrderEntity getShoppingOrder() {
        return shoppingOrder;
    }

    public OrderItemEntity setShoppingOrder(OrderEntity shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
        return this;
    }

}

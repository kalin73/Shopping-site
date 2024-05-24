package com.example.shopping.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_cart_items")
public class ShoppingItemEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCartEntity cart;

    @Column
    private int quantity;


    public ShoppingItemEntity() {

    }

    public ShoppingItemEntity(ProductEntity product, ShoppingCartEntity cart, int quantity) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public ShoppingItemEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public ShoppingCartEntity getCart() {
        return cart;
    }

    public ShoppingItemEntity setCart(ShoppingCartEntity cart) {
        this.cart = cart;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public ShoppingItemEntity setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }


}

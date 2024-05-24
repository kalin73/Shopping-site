package com.example.shopping.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCartEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "cart", targetEntity = ShoppingItemEntity.class, cascade = CascadeType.ALL)
    private List<ShoppingItemEntity> items;

    public ShoppingCartEntity(UserEntity user) {
        this.user = user;
    }

    public ShoppingCartEntity() {
        this.items = new ArrayList<>();
    }

    public UserEntity getUser() {
        return user;
    }

    public ShoppingCartEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public List<ShoppingItemEntity> getItems() {
        return items;
    }

    public ShoppingCartEntity setItems(List<ShoppingItemEntity> items) {
        this.items = items;
        return this;
    }

}

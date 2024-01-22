package com.example.shopping.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart_items")
public class ShoppingItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private ProductEntity product;

	@ManyToOne
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

	public Long getId() {
		return id;
	}

	public ShoppingItemEntity setId(Long id) {
		this.id = id;
		return this;
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

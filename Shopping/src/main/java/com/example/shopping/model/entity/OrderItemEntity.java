package com.example.shopping.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "oreder_items")
public class OrderItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public Long getId() {
		return id;
	}

	public OrderItemEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public OrderItemEntity setProduct(ProductEntity product) {
		this.product = product;
		return this;
	}

	public OrderEntity gethoppingOrder() {
		return shoppingOrder;
	}

	public OrderItemEntity setShoppingOrder(OrderEntity shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
		return this;
	}

}

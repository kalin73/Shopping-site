package com.example.shopping.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private UserEntity customer;

	@OneToMany(mappedBy = "shoppingOrder")
	private List<OrderItemEntity> items;

	@Column
	private LocalDateTime date;

	@Column(name = "order_cost")
	private BigDecimal orderCost;

	public OrderEntity() {

	}

	public Long getId() {
		return id;
	}

	public OrderEntity setId(Long id) {
		this.id = id;
		return this;
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

}

package com.example.shopping.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private UserEntity user;

	@OneToMany(mappedBy = "cart", targetEntity = ShoppingItemEntity.class, cascade = CascadeType.ALL)
	private List<ShoppingItemEntity> items;

	public ShoppingCartEntity(UserEntity user) {
		this.user = user;
	}

	public ShoppingCartEntity() {
		this.items = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public ShoppingCartEntity setId(Long id) {
		this.id = id;
		return this;
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

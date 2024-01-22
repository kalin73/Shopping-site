package com.example.shopping.model.dto;

import java.math.BigDecimal;

import com.example.shopping.model.entity.ShoppingItemEntity;

public class ShoppingCartItemDto {
	private Long id;

	private String productName;

	private BigDecimal price;

	private String image;

	private int quantity;

	private BigDecimal amount;

	public ShoppingCartItemDto() {

	}

	public ShoppingCartItemDto(String productName, BigDecimal price, String image, int quantity) {
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public ShoppingCartItemDto setId(Long id) {
		this.id = id;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public ShoppingCartItemDto setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public ShoppingCartItemDto setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public String getImage() {
		return image;
	}

	public ShoppingCartItemDto setImage(String image) {
		this.image = image;
		return this;
	}

	public int getQuantity() {
		return quantity;
	}

	public ShoppingCartItemDto setQuantity(int quantity) {
		this.quantity = quantity;
		this.amount = price.multiply(BigDecimal.valueOf(quantity));
		
		return this;
	}

	public BigDecimal getAmount() {
		return amount;
	}


	public static ShoppingCartItemDto mapToShoppingItemDto(ShoppingItemEntity shoppingItemEntity) {
		ShoppingCartItemDto shoppingItemDto = new ShoppingCartItemDto();

		shoppingItemDto.setId(shoppingItemEntity.getId())
			.setImage(shoppingItemEntity.getProduct().getImage())
			.setPrice(shoppingItemEntity.getProduct().getPrice())
			.setProductName(shoppingItemEntity.getProduct().getProductName())
			.setQuantity(shoppingItemEntity.getQuantity());

		return shoppingItemDto;
	}
}

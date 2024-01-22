package com.example.shopping.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartDto {
	private List<ShoppingCartItemDto> cartItems;
	private BigDecimal totalAmount;

	public ShoppingCartDto() {

	}

	public ShoppingCartDto(List<ShoppingCartItemDto> cartItems, BigDecimal totalAmount) {
		this.cartItems = cartItems;
		this.totalAmount = totalAmount;
	}

	public List<ShoppingCartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<ShoppingCartItemDto> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}

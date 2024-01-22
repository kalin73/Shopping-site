package com.example.shopping.model.dto;

import java.math.BigDecimal;

import com.example.shopping.model.entity.ProductEntity;

public class ProductViewDto {
	private Long id;

	private String productName;

	private BigDecimal price;

	private String image;

	private String video;

	private int quantity;

	public ProductViewDto() {

	}

	public ProductViewDto(String productName, BigDecimal price, String image, String video, int quantity) {
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.video = video;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public ProductViewDto setId(Long id) {
		this.id = id;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public ProductViewDto setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public ProductViewDto setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public String getImage() {
		return image;
	}

	public ProductViewDto setImage(String image) {
		this.image = image;
		return this;
	}

	public String getVideo() {
		return video;
	}

	public ProductViewDto setVideo(String video) {
		this.video = video;
		return this;
	}

	public int getQuantity() {
		return quantity;
	}

	public ProductViewDto setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public static ProductViewDto mapToProductDto(ProductEntity productEntity) {
		ProductViewDto productDto = new ProductViewDto();

		productDto.setId(productEntity.getId()).setImage(productEntity.getImage()).setVideo(productEntity.getVideoUrl())
				.setPrice(productEntity.getPrice()).setProductName(productEntity.getProductName())
				.setQuantity(productEntity.getQuantity());

		return productDto;
	}
}

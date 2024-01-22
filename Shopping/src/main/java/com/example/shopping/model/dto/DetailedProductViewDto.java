package com.example.shopping.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.shopping.model.entity.ProductEntity;

public class DetailedProductViewDto {
	private Long id;

	private String productName;

	private BigDecimal price;

	private String image;

	private String video;

	private String description;

	private List<SpecificationDto> specs;

	public DetailedProductViewDto() {
		this.specs = new ArrayList<>();
	}

	public DetailedProductViewDto(String productName, BigDecimal price, String image, String video, String description,
			List<SpecificationDto> specs) {
		this();
		this.productName = productName;
		this.price = price;
		this.image = image;
		this.video = video;
		this.specs = specs;
	}

	public Long getId() {
		return id;
	}

	public DetailedProductViewDto setId(Long id) {
		this.id = id;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public DetailedProductViewDto setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public DetailedProductViewDto setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public String getImage() {
		return image;
	}

	public DetailedProductViewDto setImage(String image) {
		this.image = image;
		return this;
	}

	public String getVideo() {
		return video;
	}

	public DetailedProductViewDto setVideo(String video) {
		this.video = video;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public DetailedProductViewDto setDescription(String description) {
		this.description = description;
		return this;
	}

	public List<SpecificationDto> getSpecs() {
		return specs;
	}

	public DetailedProductViewDto setSpecs(List<SpecificationDto> specs) {
		this.specs = specs;
		return this;
	}

	public static DetailedProductViewDto mapToDetailedView(ProductEntity productEntity) {
		DetailedProductViewDto product = new DetailedProductViewDto();

		product.setId(productEntity.getId()).setImage(productEntity.getImage()).setVideo(productEntity.getVideoUrl())
				.setPrice(productEntity.getPrice()).setProductName(productEntity.getProductName())
				.setDescription(productEntity.getDescription())
				.setSpecs(productEntity.getSpecs().stream().map(SpecificationDto::mapToSpecDto).toList());

		return product;
	}

}

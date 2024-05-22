package com.example.shopping.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tech_specifications")
public class SpecificationsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String value;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	public SpecificationsEntity() {

	}

	public SpecificationsEntity(String name, String value, ProductEntity product) {
		this.name = name;
		this.value = value;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public SpecificationsEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public SpecificationsEntity setName(String name) {
		this.name = name;
		return this;
	}

	public String getValue() {
		return value;
	}

	public SpecificationsEntity setValue(String value) {
		this.value = value;
		return this;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public SpecificationsEntity setProduct(ProductEntity product) {
		this.product = product;
		return this;
	}

}

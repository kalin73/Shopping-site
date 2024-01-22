package com.example.shopping.model.entity;

import com.example.shopping.model.enums.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Category name;

	public CategoryEntity() {

	}

	public CategoryEntity(Category name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getName() {
		return name;
	}

	public void setName(Category name) {
		this.name = name;
	}

}

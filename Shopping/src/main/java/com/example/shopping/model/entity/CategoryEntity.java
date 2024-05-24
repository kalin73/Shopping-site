package com.example.shopping.model.entity;

import com.example.shopping.model.enums.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{
	@Enumerated(EnumType.STRING)
	private Category name;

	public CategoryEntity() {

	}

	public CategoryEntity(Category name) {
		this.name = name;
	}

	public Category getName() {
		return name;
	}

	public void setName(Category name) {
		this.name = name;
	}

}

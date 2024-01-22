package com.example.shopping.service;

import org.springframework.stereotype.Service;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public CategoryEntity getCategoryByName(String category) {
		return this.categoryRepository.findByName(Category.valueOf(category.toUpperCase())).get();
	}

	public CategoryEntity getCategoryById(Long id) {
		return this.categoryRepository.findById(id).orElse(null);
	}
}

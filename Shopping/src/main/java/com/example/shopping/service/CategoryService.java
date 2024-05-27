package com.example.shopping.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(cacheNames = "categoryById")
    public CategoryEntity getCategoryById(long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @CacheEvict(cacheNames = "categoryById", allEntries = true)
    public void refreshCategory() {
    }
}

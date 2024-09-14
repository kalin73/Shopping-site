package com.example.shopping.service;

import com.example.shopping.model.dto.DetailedProductViewDto;
import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductViewDto> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(ProductViewDto::mapToProductDto)
                .collect(Collectors.toList());
    }

    public List<ProductViewDto> search(String prod) {
        return this.productRepository.search(prod).orElse(null)
                .stream()
                .map(ProductViewDto::mapToProductDto)
                .toList();
    }

    public List<ProductViewDto> getProductsFromCatName(String categoryName) {
        CategoryEntity category = this.categoryRepository.findByName(Category.valueOf(categoryName)).get();

        return loadItemsByCategory(category);
    }

    @Cacheable(cacheNames = "productsByCat")
    public List<ProductViewDto> getProductsFromCat(CategoryEntity category) {
        return loadItemsByCategory(category);
    }

    @Transactional
    public Optional<DetailedProductViewDto> getProductById(long id) {
        return this.productRepository.findById(id)
                .map(DetailedProductViewDto::mapToDetailedView);
    }

    private List<ProductViewDto> loadItemsByCategory(CategoryEntity category) {
        return this.productRepository.findAllByCategoryAndQuantityGreaterThan(category, 0).orElseGet(null)
                .stream()
                .map(ProductViewDto::mapToProductDto)
                .toList();
    }


    @CacheEvict(cacheNames = "productsByCat", allEntries = true)
    public void refreshProductsByCategory() {

    }

}

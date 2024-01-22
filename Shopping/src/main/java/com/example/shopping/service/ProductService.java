package com.example.shopping.service;

import com.example.shopping.model.dto.DetailedProductViewDto;
import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        List<ProductViewDto> products = this.productRepository.findAll().stream().map(ProductViewDto::mapToProductDto)
                .collect(Collectors.toList());

        return products;
    }

    public List<ProductViewDto> search(String prod) {
        List<ProductViewDto> products = this.productRepository.search(prod).orElse(null).stream()
                .map(ProductViewDto::mapToProductDto).toList();

        return products;

    }

    public List<ProductViewDto> getProductsFromCatName(String categoryName) {
        CategoryEntity category = this.categoryRepository.findByName(Category.valueOf(categoryName)).get();

        return loadItemsByCategory(category);
    }

    public List<ProductViewDto> getProductsFromCat(CategoryEntity category) {
        return loadItemsByCategory(category);
    }

    @Transactional
    public DetailedProductViewDto getProductById(Long id) {
        DetailedProductViewDto product = this.productRepository.findById(id)
                .map(DetailedProductViewDto::mapToDetailedView).get();

        return product;
    }

    private List<ProductViewDto> loadItemsByCategory(CategoryEntity category) {
        List<ProductViewDto> products = this.productRepository.findAllByCategoryAndQuantityGreaterThan(category, 0).orElseGet(null).stream()
                .map(ProductViewDto::mapToProductDto).toList();

        return products;
    }

}

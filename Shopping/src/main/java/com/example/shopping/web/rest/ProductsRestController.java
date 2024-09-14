package com.example.shopping.web.rest;

import com.example.shopping.model.dto.DetailedProductViewDto;
import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.service.CategoryService;
import com.example.shopping.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductsRestController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private long categoryId;

    public ProductsRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductViewDto>> getAllProducts() {
        List<ProductViewDto> products = this.productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{catId}")
    public ResponseEntity<List<ProductViewDto>> getAllProductsFromCategory(@PathVariable(name = "catId") Long catId) {
        if (categoryId != catId) {
            this.productService.refreshProductsByCategory();
            this.categoryService.refreshCategory();
            this.categoryId = catId;
        }
        CategoryEntity category = this.categoryService.getCategoryById(catId);
        List<ProductViewDto> products = this.productService.getProductsFromCat(category);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/info/{id}")
    public ResponseEntity<DetailedProductViewDto> getProductById(@PathVariable(name = "id") Long id) {
        Optional<DetailedProductViewDto> product = this.productService.getProductById(id);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

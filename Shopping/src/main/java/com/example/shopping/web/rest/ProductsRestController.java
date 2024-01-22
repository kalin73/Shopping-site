package com.example.shopping.web.rest;

import com.example.shopping.model.dto.DetailedProductViewDto;
import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.service.CategoryService;
import com.example.shopping.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductsRestController {
	private final ProductService productService;
	private final CategoryService categoryService;

	public ProductsRestController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductViewDto>> getAllProducts() {
		List<ProductViewDto> products = this.productService.getAllProducts();

		return ResponseEntity.ok(products);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<List<ProductViewDto>> getProducts(@PathVariable(name = "id") Long id) {
		CategoryEntity category = this.categoryService.getCategoryById(id);
		List<ProductViewDto> products = this.productService.getProductsFromCat(category);

		return ResponseEntity.ok(products);
	}

	@GetMapping("/product/info/{id}")
	public ResponseEntity<DetailedProductViewDto> getProduct(@PathVariable(name = "id") Long id) {
		DetailedProductViewDto product = this.productService.getProductById(id);

		return ResponseEntity.ok(product);
	}
}

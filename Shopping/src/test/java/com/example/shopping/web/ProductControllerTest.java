package com.example.shopping.web;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    private static Long catId = 1L;
    private static Long product1Id = 1L;
    private static Long product2Id = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        CategoryEntity category = categoryRepository.save(new CategoryEntity(Category.COMPUTERS));
        catId = category.getId();

        ProductEntity product1 = new ProductEntity();
        product1.setProductName("PC1")
                .setPrice(BigDecimal.TEN)
                .setQuantity(3)
                .setCategory(category);
        product1Id = productRepository.save(product1).getId();

        ProductEntity product2 = new ProductEntity();
        product2.setProductName("PC2")
                .setPrice(BigDecimal.ONE)
                .setQuantity(5)
                .setCategory(category);
        product2Id = productRepository.save(product2).getId();

    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testSearch() throws Exception {
        mockMvc.perform(get("/products/search")
                        .param("filter", "pc"))
                .andExpect(view().name("searchPage"))
                .andExpect(model().attribute("products", Matchers.iterableWithSize(2)));
    }

    @Test
    public void testGetProductsFromCategory() throws Exception {
        mockMvc.perform(get("/products/{id}", catId))
                .andExpect(view().name("productsPage"))
                .andExpect(model().attribute("id", catId));

    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/info/{id}", product1Id))
                .andExpect(view().name("productInfo"))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attribute("id", product1Id));

        mockMvc.perform(get("/products/info/{id}", product2Id))
                .andExpect(view().name("productInfo"))
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attribute("id", product2Id));

    }
}

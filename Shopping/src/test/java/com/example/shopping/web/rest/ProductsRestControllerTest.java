package com.example.shopping.web.rest;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductsRestControllerTest {
    private static Long product1Id;
    private static Long product2Id;
    private static Long catId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        CategoryEntity laptopCategory = new CategoryEntity();
        laptopCategory.setName(Category.LAPTOPS);
        if (this.categoryRepository.count() == 0) {
            catId = this.categoryRepository.save(laptopCategory).getId();
        }

        ProductEntity product1 = new ProductEntity();
        product1.setProductName("ASUS VIVOBOOK")
                .setPrice(BigDecimal.valueOf(2000))
                .setDescription("Good laptop")
                .setQuantity(1)
                .setCategory(laptopCategory);

        ProductEntity product2 = new ProductEntity();
        product2.setProductName("Acer NITRO 5")
                .setPrice(BigDecimal.valueOf(1299))
                .setDescription("Good laptop")
                .setQuantity(2)
                .setCategory(laptopCategory);

        product1Id = this.productRepository.save(product1).getId();
        product2Id = this.productRepository.save(product2).getId();
    }

    @AfterEach
    public void tearDown() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productName", is("ASUS VIVOBOOK")))
                .andExpect(jsonPath("$.[1].productName", is("Acer NITRO 5")))
                .andExpect(jsonPath("$.[0].price", is(2000.00)))
                .andExpect(jsonPath("$.[1].price", is(1299.00)))
                .andExpect(jsonPath("$.[0].quantity", is(1)))
                .andExpect(jsonPath("$.[1].quantity", is(2)));
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/api/product/info/{id}", product1Id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("ASUS VIVOBOOK"))
                .andExpect(jsonPath("$.price").value(2000.00))
                .andExpect(jsonPath("$.description").value("Good laptop"));

        mockMvc.perform(get("/api/product/info/{id}", product2Id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Acer NITRO 5"))
                .andExpect(jsonPath("$.price").value(1299.00))
                .andExpect(jsonPath("$.description").value("Good laptop"));

    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        mockMvc.perform(get("/api/products/{catId}", catId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productName", is("ASUS VIVOBOOK")))
                .andExpect(jsonPath("$.[1].productName", is("Acer NITRO 5")));
    }
}

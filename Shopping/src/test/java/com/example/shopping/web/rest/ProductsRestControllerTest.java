package com.example.shopping.web.rest;

import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.getAllProducts())
                .thenReturn(List.of(createProduct("ACER", 1500), createProduct("ASUS", 2000)));

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productName", is("ACER")))
                .andExpect(jsonPath("$.[1].productName", is("ASUS")))
                .andExpect(jsonPath("$.[0].price", is(1500)))
                .andExpect(jsonPath("$.[1].price", is(2000)));
    }

    private ProductViewDto createProduct(String name, int price) {
        return new ProductViewDto(name, BigDecimal.valueOf(price), "path", "url", 1);
    }
}

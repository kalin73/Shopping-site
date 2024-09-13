package com.example.shopping.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].productName", is("ASUS VIVOBOOK")))
                .andExpect(jsonPath("$.[1].productName", is("Acer NITRO 5")))
                .andExpect(jsonPath("$.[0].price", is(2000.00)))
                .andExpect(jsonPath("$.[1].price", is(1299.00)));
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/api/product/info/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("ASUS VIVOBOOK"))
                .andExpect(jsonPath("$.price").value(2000.00))
                .andExpect(jsonPath("$.description").value("Good laptop"));

    }
}

package com.example.shopping.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAboutUsPage() throws Exception {
        mockMvc.perform(get("/aboutUs"))
                .andExpect(status().isOk());
    }

    @Test
    public void getReviewsPage() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }
}

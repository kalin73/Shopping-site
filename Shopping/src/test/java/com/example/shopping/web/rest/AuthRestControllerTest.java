package com.example.shopping.web.rest;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testRegisterUser() throws Exception {
        final RegisterFormDto registerFormDto = new RegisterFormDto();
        final String testEmail = "test@test.com";

        registerFormDto.setEmail(testEmail)
                .setFirstName("Test")
                .setLastName("Testov")
                .setPassword("12345")
                .setPhoneNumber("0947827452");

        final String json = new Gson().toJson(registerFormDto);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        UserEntity user = userRepository.findByEmail(testEmail).orElse(null);

        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("Testov", user.getLastName());
        assertEquals("0947827452", user.getPhoneNumber());
    }
}

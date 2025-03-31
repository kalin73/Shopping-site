package com.example.shopping.web;

import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.AddressRepository;
import com.example.shopping.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        UserEntity user = new UserEntity("Test", "Testov", "test@abv.bg", "topsecret", "0987654321");
        user.setEnabled(true);

        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "kalin")
    public void testAddressFormWhenLoggedIn() throws Exception {
        mockMvc.perform(get("/billingAddress"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddressFormWhenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/billingAddress"))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails(value = "test@abv.bg", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void testAddingAddress() throws Exception {
        mockMvc.perform(post("/billingAddress")
                        .param("fullName", "Test Testov")
                        .param("phoneNumber", "0982746271")
                        .param("country", "Bulgaria")
                        .param("city", "Sofia")
                        .param("address", "Mladost"))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, addressRepository.count());
    }

    @Test
    @WithUserDetails(value = "test@abv.bg", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void testAddingAddressWithWrongData() throws Exception {
        mockMvc.perform(post("/billingAddress")
                        .param("fullName", "Test Testov")
                        .param("phoneNumber", "098274627111111")
                        .param("country", "Bulgaria")
                        .param("city", "Sofia")
                        .param("address", "Mladost"))
                .andExpect(status().isBadRequest());

        assertEquals(0, addressRepository.count());
    }

}

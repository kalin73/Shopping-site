package com.example.shopping.web;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.AddressRepository;
import com.example.shopping.repository.ConfirmationRepository;
import com.example.shopping.repository.UserRepository;
import com.example.shopping.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        registerUser();
    }

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
        confirmationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetProfilePage() throws Exception {
        addAddress();
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profilePage"))
                .andExpect(model().attribute("fullName", "Test Testov"))
                .andExpect(model().attribute("phoneNumber", "0982746271"))
                .andExpect(model().attribute("email", "test@abv.bg"))
                .andExpect(model().attribute("country", "Bulgaria"))
                .andExpect(model().attribute("city", "Sofia"));
    }

    @Test
    public void testGetProfilePageWhenAddressIsNotAdded() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isNotFound());
    }

    private void addAddress() throws Exception {
        mockMvc.perform(post("/billingAddress")
                .param("fullName", "Test Testov")
                .param("phoneNumber", "0982746271")
                .param("country", "Bulgaria")
                .param("city", "Sofia")
                .param("address", "Mladost"));
    }


    private void registerUser() {
        RegisterFormDto user = new RegisterFormDto();
        user.setFirstName("Test");
        user.setLastName("Testov");
        user.setEmail("test@abv.bg");
        user.setPassword("topsecret");
        user.setPhoneNumber("0982746271");

        userService.registerUser(user);

        UserEntity verifiedUser = userRepository
                .findByEmail("test@abv.bg")
                .orElseThrow()
                .setEnabled(true);

        userRepository.save(verifiedUser);
    }
}

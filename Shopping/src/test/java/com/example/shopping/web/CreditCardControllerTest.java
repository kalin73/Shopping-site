package com.example.shopping.web;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ConfirmationRepository;
import com.example.shopping.repository.CreditCardRepository;
import com.example.shopping.repository.UserRepository;
import com.example.shopping.service.UserService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class CreditCardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @BeforeEach
    public void setUp() {
        RegisterFormDto user = new RegisterFormDto();
        user.setEmail("test@abv.bg");
        user.setPassword("topsecret");
        user.setPhoneNumber("0987654321");

        userService.registerUser(user);

        UserEntity verifiedUser = userRepository
                .findByEmail("test@abv.bg")
                .orElseThrow()
                .setEnabled(true);

        userRepository.save(verifiedUser);
    }

    @AfterEach
    public void tearDown() {
        this.creditCardRepository.deleteAll();
        this.confirmationRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "kalin")
    public void testGetCreditCardFormPageWithLoggedUser() throws Exception {
        mockMvc.perform(get("/creditCard/cardInformation"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCreditCardFormPageWithoutLoggedUser() throws Exception {
        mockMvc.perform(get("/creditCard/cardInformation"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testAddingCreditCardWithLoggedUser() throws Exception {
        assertEquals(0, creditCardRepository.count());

        mockMvc.perform(post("/creditCard/add")
                        .params(getCreditCardParams()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        assertEquals(1, creditCardRepository.count());
    }

    @Test
    public void testAddingCreditCardWithoutLoggedUser() throws Exception {
        mockMvc.perform(post("/creditCard/add")
                        .params(getCreditCardParams()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testAddingCreditCardWithWrongData() throws Exception {
        assertEquals(0, creditCardRepository.count());

        mockMvc.perform(post("/creditCard/add")
                        .param("ownerName", "Test Testov")
                        .param("cardNumber", "1234 5678 9012")
                        .param("cvcCode", "1")
                        .param("month", "01")
                        .param("year", "2028"))
                .andExpect(status().isBadRequest());

        assertEquals(0, creditCardRepository.count());

    }

    private MultiValueMap<String, String> getCreditCardParams() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ownerName", "Test Testov");
        map.add("cardNumber", "1234 5678 9012 3456");
        map.add("cvcCode", "123");
        map.add("month", "01");
        map.add("year", "2028");

        return map;
    }
}

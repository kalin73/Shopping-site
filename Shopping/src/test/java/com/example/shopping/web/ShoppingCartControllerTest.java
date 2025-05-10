package com.example.shopping.web;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.*;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {
    private static Long product1Id = 1L;
    private static UserEntity registeredUser = null;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    public void setUp() {
        addProducts();
        registerUser();
    }

    @AfterEach
    public void tearDown() {
        shoppingItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        confirmationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testAddToCart() throws Exception {
        assertEquals(0, shoppingCartRepository.count());

        mockMvc.perform(get("/add/{id}", product1Id))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, shoppingCartRepository.count());
    }

    @Test
    public void testAddToCartWhenNotLoggedIn() throws Exception {
        assertEquals(0, shoppingCartRepository.count());

        mockMvc.perform(get("/add/{id}", product1Id))
                .andExpect(status().is3xxRedirection());

        assertEquals(0, shoppingCartRepository.count());
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testDeleteCart() throws Exception {
        mockMvc.perform(get("/add/{id}", product1Id))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, shoppingCartRepository.count());

        mockMvc.perform(get("/deleteCart", product1Id))
                .andExpect(status().is3xxRedirection());

        assertEquals(0, shoppingCartRepository.count());
    }

    @Test
    public void testDeleteCartWhenNotLoggedIn() throws Exception {
        shoppingCartRepository.save(new ShoppingCartEntity(registeredUser));
        assertTrue(shoppingCartRepository.findByUser(registeredUser).isPresent());

        mockMvc.perform(get("/deleteCart", product1Id))
                .andExpect(status().is3xxRedirection());

        assertTrue(shoppingCartRepository.findByUser(registeredUser).isPresent());
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testRemoveItemFromCart() throws Exception {
        mockMvc.perform(get("/add/{id}", product1Id));
        mockMvc.perform(get("/add/{id}", product1Id));

        ShoppingCartEntity cart = shoppingCartRepository.findByUser(registeredUser).orElse(null);
        List<ShoppingItemEntity> items = shoppingItemRepository.findAllByCart(cart).orElse(new ArrayList<>());

        assertEquals(1, items.size());
        assertEquals(2, items.get(0).getQuantity());

        Long itemId = items.get(0).getId();

        mockMvc.perform(get("/delete/{id}", itemId))
                .andExpect(status().is3xxRedirection());

        items = shoppingItemRepository.findAllByCart(cart).orElse(new ArrayList<>());

        assertEquals(1, items.size());
        assertEquals(1, items.get(0).getQuantity());
        assertEquals(1, shoppingCartRepository.count());

        mockMvc.perform(get("/delete/{id}", itemId))
                .andExpect(status().is3xxRedirection());

        assertEquals(0, shoppingCartRepository.count());
        assertEquals(0, shoppingItemRepository.findAllByCart(cart).orElse(new ArrayList<>()).size());
    }

    private void addProducts() {
        CategoryEntity category = categoryRepository.save(new CategoryEntity(Category.COMPUTERS));

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
        productRepository.save(product2);
    }

    private void registerUser() {
        RegisterFormDto user = new RegisterFormDto();
        user.setEmail("test@abv.bg");
        user.setPassword("topsecret");
        user.setPhoneNumber("0987654321");

        userService.registerUser(user);

        UserEntity verifiedUser = userRepository
                .findByEmail("test@abv.bg")
                .orElseThrow()
                .setEnabled(true);

        registeredUser = userRepository.save(verifiedUser);
    }
}

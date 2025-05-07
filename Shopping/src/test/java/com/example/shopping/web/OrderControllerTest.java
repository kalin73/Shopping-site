package com.example.shopping.web;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.model.enums.Category;
import com.example.shopping.repository.*;
import com.example.shopping.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private static final String TEST_EMAIL = "test@abv.bg";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeEach
    public void setUp() {
        RegisterFormDto user = new RegisterFormDto();
        user.setEmail(TEST_EMAIL);
        user.setPassword("topsecret");
        user.setPhoneNumber("0987654321");

        userService.registerUser(user);

        UserEntity verifiedUser = userRepository
                .findByEmail(TEST_EMAIL)
                .orElseThrow()
                .setEnabled(true);

        userRepository.save(verifiedUser);


    }

    @AfterEach
    public void tearDown() {
        confirmationRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        shoppingItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetCheckOutPage() throws Exception {
        addToCart();

        mockMvc.perform(get("/order/checkout"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCheckOutPageWithoutItemsInCart() throws Exception {
        mockMvc.perform(get("/order/checkout"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPlacingOrderWithWrongData() throws Exception {
        mockMvc.perform(post("/order/placeOrder")
                        .param("fullName", "Test Testov")
                        .param("phoneNumber", "098274627111111")
                        .param("shippingAddress", "st")
                        .param("paymentMethod", "CASH"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPlacingOrderWithCardPaymentMethod() throws Exception {
        mockMvc.perform(post("/order/placeOrder")
                        .param("fullName", "Test Testov")
                        .param("phoneNumber", "0987654321")
                        .param("shippingAddress", "Test city")
                        .param("paymentMethod", "CARD"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testPlacingOrderWithCashPaymentMethod() throws Exception {
        addToCart();

        assertEquals(0, orderRepository.count());

        mockMvc.perform(post("/order/placeOrder")
                        .param("fullName", "Test Testov")
                        .param("phoneNumber", "0987654321")
                        .param("shippingAddress", "Test city")
                        .param("paymentMethod", "CASH"))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, orderRepository.count());
    }

    private void addToCart() throws Exception {
        CategoryEntity category = new CategoryEntity(Category.COMPUTERS);
        category = categoryRepository.save(category);

        ProductEntity product = new ProductEntity();
        product.setProductName("PC")
                .setPrice(BigDecimal.TEN)
                .setQuantity(3)
                .setCategory(category);
        product = productRepository.save(product);

        //Adding product to shopping cart
        mockMvc.perform(get("/add/{id}", product.getId()));
    }
}

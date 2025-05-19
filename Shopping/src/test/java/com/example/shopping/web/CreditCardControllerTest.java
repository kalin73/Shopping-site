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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

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
        confirmationRepository.deleteAll();
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        shoppingItemRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        productRepository.deleteAll();
        creditCardRepository.deleteAll();
        userRepository.deleteAll();
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

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testPayWithCardWhenLoggedIn() throws Exception {
        assertEquals(0, orderRepository.count());

        placeOrder();

        mockMvc.perform(post("/creditCard/payWithCard")
                        .params(getCreditCardParams()))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, orderRepository.count());
    }

    @Test
    public void testPayWithCardWithoutLoggedUser() throws Exception {
        assertEquals(0, orderRepository.count());

        placeOrder();

        mockMvc.perform(post("/creditCard/payWithCard")
                        .params(getCreditCardParams()))
                .andExpect(status().is3xxRedirection());

        assertEquals(0, orderRepository.count());
    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testPayWithCardWithWrongData() throws Exception {
        assertEquals(0, orderRepository.count());

        placeOrder();

        mockMvc.perform(post("/creditCard/payWithCard")
                        .param("ownerName", "Test Testov")
                        .param("cardNumber", "1234 5678 9012")
                        .param("cvcCode", "1")
                        .param("month", "01")
                        .param("year", "2028"))
                .andExpect(status().isBadRequest())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("creditCardForm", "cardNumber"))
                .andExpect(model().attributeHasFieldErrors("creditCardForm", "cvcCode"));

        assertEquals(0, orderRepository.count());

    }

    @Test
    @WithUserDetails(setupBefore = TestExecutionEvent.TEST_EXECUTION, value = "test@abv.bg")
    public void testPayAndSaveCard() throws Exception {
        assertEquals(0, orderRepository.count());
        assertEquals(0, creditCardRepository.count());
        placeOrder();

        mockMvc.perform(post("/creditCard/payWithCard")
                        .params(getCreditCardParams())
                        .param("save", "true"))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, orderRepository.count());
        assertEquals(1, creditCardRepository.count());
    }

    private void placeOrder() throws Exception {
        addToCart();
        mockMvc.perform(post("/order/placeOrder")
                .param("fullName", "Test Testov")
                .param("phoneNumber", "0987654321")
                .param("shippingAddress", "Test city")
                .param("paymentMethod", "CARD"));
    }

    private void addToCart() throws Exception {
        CategoryEntity category = new CategoryEntity(Category.COMPUTERS);
        category = categoryRepository.save(category);

        ProductEntity product = new ProductEntity();
        product.setProductName("PC")
                .setPrice(BigDecimal.valueOf(100000))
                .setQuantity(3)
                .setCategory(category);
        product = productRepository.save(product);

        //Adding product to shopping cart
        mockMvc.perform(get("/add/{id}", product.getId()));
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

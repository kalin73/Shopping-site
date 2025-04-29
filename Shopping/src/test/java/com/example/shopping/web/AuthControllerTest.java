package com.example.shopping.web;

import com.example.shopping.exceptions.ExpiredTokenException;
import com.example.shopping.model.entity.ConfirmationEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ConfirmationRepository;
import com.example.shopping.repository.UserRepository;
import com.example.shopping.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    public void tearDown() {
        confirmationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk());

    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .params(getValidRegisterParams()))
                .andExpect(status().is3xxRedirection());

        assertEquals(1, userRepository.count());
    }

    @Test
    public void testVerifyEmail() throws Exception {
        mockMvc.perform(post("/auth/register")
                .params(getValidRegisterParams()));

        UserEntity user = userRepository.findByEmail("test@abv.bg").orElse(null);
        assertNotNull(user);
        assertFalse(user.getEnabled());

        ConfirmationEntity confirmation = confirmationRepository.findAllByUser(user).get(0);

        mockMvc.perform(get("/auth/verify")
                        .param("token", confirmation.getToken()))
                .andExpect(status().is3xxRedirection());

        UserEntity updatedUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertNotNull(updatedUser);
        assertTrue(updatedUser.getEnabled());
    }

    @Test
    public void testRegistrationWithWrongData() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .params(getInvalidRegisterParams()))
                .andExpect(status().isBadRequest());

        assertEquals(0, userRepository.count());
    }

    @Test
    public void testGetForgotPasswordPage() throws Exception {
        mockMvc.perform(get("/auth/forgotPassword"))
                .andExpect(status().isOk());
    }

    @Test
    public void testForgotPasswordWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(post("/auth/forgotPassword")
                        .param("email", "test1@abv.bg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPasswordWhenUserExist() throws Exception {
        addUser(false);
        mockMvc.perform(post("/auth/forgotPassword")
                        .param("email", "test@abv.bg"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChangePasswordPage() throws Exception {
        UserEntity user = addUser(false);
        mockMvc.perform(post("/auth/forgotPassword")
                        .param("email", "test@abv.bg"))
                .andExpect(status().isOk());

        ConfirmationEntity confirmation = this.confirmationRepository.findAllByUser(user).get(0);

        mockMvc.perform(get("/auth/changePassword")
                        .param("token", confirmation.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangingPassword() throws Exception {
        mockMvc.perform(post("/auth/register")
                .params(getValidRegisterParams()));

        UserEntity user = userRepository.findByEmail("test@abv.bg").orElse(null);

        assertNotNull(user);

        String oldPassword = user.getPassword();
        String newPassword = "newTopSecretPassword";

        mockMvc.perform(post("/auth/changePassword")
                        .param("email", user.getEmail())
                        .param("password", newPassword)
                        .param("confirmPassword", newPassword))
                .andExpect(status().isOk());

        UserEntity updatedUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertNotNull(updatedUser);
        assertNotEquals(oldPassword, updatedUser.getPassword());
    }

    @Test
    public void testChangePasswordWhenConfirmPasswordDoesNotMatch() throws Exception {
        UserEntity user = addUser(false);
        String newPassword = "newTopSecretPassword";

        mockMvc.perform(post("/auth/changePassword")
                        .param("email", user.getEmail())
                        .param("password", newPassword)
                        .param("confirmPassword", "<WRONG_PASSWORD>"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginErrorWithUnverifiedAccount() throws Exception {
        UserEntity user = addUser(false);

        mockMvc.perform(post("/auth/login")
                .param("email", user.getEmail())
                .param("password", "topsecret"));

        mockMvc.perform(post("/auth/login-error"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginErrorWithWrongPasswordOrEmail() throws Exception {
        UserEntity user = addUser(true);

        mockMvc.perform(post("/auth/login")
                .param("email", user.getEmail())
                .param("password", "topsecret123"));

        mockMvc.perform(post("/auth/login-error"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSendVerificationEmail() throws Exception {
        mockMvc.perform(post("/auth/sendVerification")
                        .param("email", "test@abv.bg"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testExpiredTokenExceptionHandler() throws Exception {
        mockMvc.perform(post("/auth/register")
                .params(getValidRegisterParams()));

        UserEntity user = userRepository.findByEmail("test@abv.bg").orElse(null);
        assertNotNull(user);

        ConfirmationEntity confirmation = this.confirmationRepository.findAllByUser(user).get(0);
        confirmation.setExpire(confirmation.getCreated());
        confirmationRepository.save(confirmation);

        assertThrows(ExpiredTokenException.class, () -> userService.verifyToken(confirmation.getToken()));

        mockMvc.perform(get("/auth/verify")
                .param("token", confirmation.getToken()));
    }

    private UserEntity addUser(boolean verified) {
        UserEntity user = new UserEntity();
        user.setEmail("test@abv.bg");
        user.setPassword("topsecret");
        user.setEnabled(verified);
        user.setFirstName("Test");
        user.setLastName("Testov");
        user.setPhoneNumber("0987654321");

        return userRepository.save(user);
    }


    private MultiValueMap<String, String> getValidRegisterParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("firstName", List.of("Test"));
        params.put("lastName", List.of("Testov"));
        params.put("email", List.of("test@abv.bg"));
        params.put("password", List.of("topsecret"));
        params.put("phoneNumber", List.of("0987654321"));

        return params;
    }

    private MultiValueMap<String, String> getInvalidRegisterParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("firstName", List.of(""));
        params.put("lastName", List.of("Testov"));
        params.put("email", List.of("test@"));
        params.put("password", List.of("topsecret"));
        params.put("phoneNumber", List.of("0987654"));

        return params;
    }
}

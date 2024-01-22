package com.example.shopping.service;

import static org.mockito.ArgumentMatchers.any;

import com.example.shopping.repository.ConfirmationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private UserService userService;

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private ConfirmationRepository confirmationRepository;

	@Mock
	private EmailService emailService;

	@BeforeEach
	void setUp() {
		userService = new UserService(mockUserRepository, passwordEncoder, confirmationRepository, emailService);
	}

	@Test
	public void testUserRegistration() {
		RegisterFormDto userRegisterForm = new RegisterFormDto();
		userRegisterForm.setEmail("ivan@abv.bg").setFirstName("Ivan").setLastName("Ivanov");

		userService.registerUser(userRegisterForm);

		Mockito.verify(mockUserRepository).save(any());
	}
}

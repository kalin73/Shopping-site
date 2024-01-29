package com.example.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ConfirmationRepository;
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

	@Captor
	private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

	@BeforeEach
	void setUp() {
		userService = new UserService(mockUserRepository, passwordEncoder, confirmationRepository, emailService);
	}

	@Test
	public void testUserRegistration() {
		final String testPassword = "topsecret";
		final String encodedPassword = "encoded_password";
		final String email = "test@example.com";
		final String firstName = "Test";
		final String lastName = "Testov";
		final String phone = "1234567890";

		RegisterFormDto userRegisterForm = new RegisterFormDto();
		userRegisterForm.setEmail(email).setFirstName(firstName).setLastName(lastName).setPassword(testPassword)
				.setPhoneNumber(phone);

		when(passwordEncoder.encode(testPassword)).thenReturn(encodedPassword);
		when(mockUserRepository.save(any())).thenReturn(new UserEntity());

		userService.registerUser(userRegisterForm);

		verify(mockUserRepository).save(userEntityArgumentCaptor.capture());

		UserEntity mockUserEntity = userEntityArgumentCaptor.getValue();

		assertEquals(encodedPassword, mockUserEntity.getPassword());
		assertEquals(firstName, mockUserEntity.getFirstName());
		assertEquals(lastName, mockUserEntity.getLastName());
		assertEquals(email, mockUserEntity.getEmail());
		assertEquals(phone, mockUserEntity.getPhoneNumber());

	}
}

package com.example.shopping.model.dto;

import java.io.Serial;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ApplicationUserDetails extends User {
	@Serial
	private static final long serialVersionUID = 1L;
	private String fullName;
	private String phoneNumber;

	public ApplicationUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities,
			String phoneNumber, String fullName) {
		super(email, password, authorities);
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public ApplicationUserDetails setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public ApplicationUserDetails setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
}

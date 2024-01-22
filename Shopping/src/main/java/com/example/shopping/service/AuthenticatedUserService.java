package com.example.shopping.service;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static com.example.shopping.utils.Utils.IS_VALID;

public class AuthenticatedUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " doesn't exist!"));

        if (!userEntity.getEnabled()) {
            IS_VALID = false;
            throw new UsernameNotFoundException("Please verify your email!");
        }

        ApplicationUserDetails user = new ApplicationUserDetails(email, userEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_")), userEntity.getPhoneNumber(),
                userEntity.getFirstName() + " " + userEntity.getLastName());

        IS_VALID = true;

        return user;
    }

}

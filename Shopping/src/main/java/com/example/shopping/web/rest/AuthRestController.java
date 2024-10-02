package com.example.shopping.web.rest;

import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.dto.UserDto;
import com.example.shopping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final UserService userService;

    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterFormDto userRegisterForm) {
        UserDto user = this.userService.registerUser(userRegisterForm);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}

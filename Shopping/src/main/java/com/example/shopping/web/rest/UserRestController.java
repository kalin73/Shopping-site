package com.example.shopping.web.rest;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.UserProfileDto;
import com.example.shopping.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfileDto> getUserProfile(@AuthenticationPrincipal ApplicationUserDetails authUser) {
        if (authUser == null) {
            return ResponseEntity.status(401).build();
        }

        UserProfileDto user = userService.getUserProfile(authUser.getUsername());

        return ResponseEntity.ok(user);
    }
}

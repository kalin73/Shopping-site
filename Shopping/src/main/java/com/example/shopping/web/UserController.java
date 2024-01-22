package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.UserProfileDto;
import com.example.shopping.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView getProfilePage(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        UserProfileDto userDto = this.userService.getUserProfile(user.getUsername());

        modelAndView.addObject("email", userDto.getEmail())
                .addObject("fullName", user.getFullName())
                .addObject("phoneNumber", user.getPhoneNumber())
                .addObject("country", userDto.getDeliveryAddresses().get(0).getCountry())
                .addObject("city", userDto.getDeliveryAddresses().get(0).getCity())
                .setViewName("profilePage");

        return modelAndView;
    }
}

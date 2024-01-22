package com.example.shopping.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.ShoppingCartService;

@Controller
public class HomeController {
    private final ShoppingCartService shoppingCartService;

    public HomeController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/")
    public ModelAndView getHome(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        modelAndView.setViewName("Shopping");

        return modelAndView;
    }

    @GetMapping("/aboutUs")
    public ModelAndView getAboutUs(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);
        modelAndView.setViewName("AboutUs");

        return modelAndView;
    }

    @GetMapping("/reviews")
    public ModelAndView getReviews(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);
        modelAndView.setViewName("Review");

        return modelAndView;
    }
}

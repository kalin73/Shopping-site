package com.example.shopping.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.ShoppingCartService;

import static com.example.shopping.utils.Utils.*;


@Controller
public class HomeController {
    private final ShoppingCartService shoppingCartService;
    private final HttpServletRequest request;

    public HomeController(ShoppingCartService shoppingCartService, HttpServletRequest request) {
        this.shoppingCartService = shoppingCartService;
        this.request = request;
    }

    @GetMapping("/")
    public ModelAndView getHome(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        lastUrl = request.getRequestURI();

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/aboutUs")
    public ModelAndView getAboutUs(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);
        modelAndView.setViewName("AboutUs");

        lastUrl = request.getRequestURI();

        return modelAndView;
    }

    @GetMapping("/reviews")
    public ModelAndView getReviews(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);
        modelAndView.setViewName("Review");

        lastUrl = request.getRequestURI();

        return modelAndView;
    }
}

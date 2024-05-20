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
public class CategoriesController {
    private final ShoppingCartService shoppingCartService;
    private final HttpServletRequest request;

    public CategoriesController(ShoppingCartService shoppingCartService, HttpServletRequest request) {
        this.shoppingCartService = shoppingCartService;
        this.request = request;
    }

    @GetMapping("/categories")
    public ModelAndView getCategories(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        lastUrl = request.getRequestURI();

        modelAndView.setViewName("Categories");

        return modelAndView;
    }
}

package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.ShoppingCartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static com.example.shopping.utils.Utils.lastUrl;

@Controller
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/add/{id}")
    public ModelAndView addItem(@PathVariable("id") Long id, @AuthenticationPrincipal ApplicationUserDetails user,
                                ModelAndView modelAndView) {
        if (user != null) {
            this.shoppingCartService.addToCart(id, user.getUsername());
        }

        modelAndView.setViewName("redirect:" + lastUrl);

        return modelAndView;

    }

    @GetMapping("/deleteCart")
    public ModelAndView deleteCart(@AuthenticationPrincipal ApplicationUserDetails user, ModelAndView modelAndView) {
        if (user != null) {
            this.shoppingCartService.deleteCart(user.getUsername());
        }

        modelAndView.setViewName("redirect:" + lastUrl);

        return modelAndView;

    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteItemById(@PathVariable("id") Long id, ModelAndView modelAndView) {
        this.shoppingCartService.removeItemFromCart(id);

        modelAndView.setViewName("redirect:" + lastUrl);

        return modelAndView;

    }
}

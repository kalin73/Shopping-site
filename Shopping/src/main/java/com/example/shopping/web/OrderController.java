package com.example.shopping.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.ShoppingCartService;

@Controller
public class OrderController {
	private final ShoppingCartService shoppingCartService;

	public OrderController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	@GetMapping("/checkout")
	public ModelAndView placeOrder(@AuthenticationPrincipal ApplicationUserDetails user, ModelAndView modelAndView) {
		this.shoppingCartService.loadShoppingCart(modelAndView, user);
		modelAndView.setViewName("cardInformation");

		return modelAndView;
	}

}

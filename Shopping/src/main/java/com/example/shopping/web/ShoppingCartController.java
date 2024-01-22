package com.example.shopping.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.ShoppingCartService;
import com.example.shopping.service.ShoppingItemService;

@Controller
public class ShoppingCartController {
	private final ShoppingCartService shoppingCartService;
	private final ShoppingItemService shoppingItemService;

	public ShoppingCartController(ShoppingCartService shoppingCartService, ShoppingItemService shoppingItemService) {
		this.shoppingCartService = shoppingCartService;
		this.shoppingItemService = shoppingItemService;
	}

	@GetMapping("/add/{id}")
	public ModelAndView addItem(@PathVariable("id") Long id, @AuthenticationPrincipal ApplicationUserDetails user,
			ModelAndView modelAndView) {
		Long categoryId = 0L;

		if (user != null) {
			categoryId = this.shoppingCartService.addToCart(id, user.getUsername());
		}

		modelAndView.setViewName("redirect:/products/" + categoryId);
		this.shoppingItemService.refreshItems();
		
		return modelAndView;

	}

	@GetMapping("/deleteCart")
	public ModelAndView deleteCart(@AuthenticationPrincipal ApplicationUserDetails user, ModelAndView modelAndView) {
		if (user != null) {
			this.shoppingCartService.deleteCart(user.getUsername());
		}

		modelAndView.setViewName("redirect:/");

		return modelAndView;

	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteItemById(@PathVariable("id") Long id, ModelAndView modelAndView) {
		this.shoppingItemService.removeItemById(id);

		modelAndView.setViewName("redirect:/");
		
		this.shoppingItemService.refreshItems();

		return modelAndView;

	}
}

package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.service.CreditCardService;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ShoppingCartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final ShoppingCartService shoppingCartService;

    private final CreditCardService creditCardService;
    private final OrderService orderService;

    public OrderController(ShoppingCartService shoppingCartService, CreditCardService creditCardService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.creditCardService = creditCardService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String placeOrder() {

        return "orderCheckout";
    }

    @PostMapping("/payWithCash")
    public void placeOrder(@AuthenticationPrincipal ApplicationUserDetails client,
                           @RequestBody OrderDto orderDto) {

        this.orderService.placeOrder(orderDto, client);
    }

    @PostMapping("/payWithCard")
    public void payWithCard(boolean save,
                              CreditCardForm creditCardForm,
                              @RequestBody OrderDto orderDto,
                              @AuthenticationPrincipal ApplicationUserDetails user) {
        if (save) {
            addCard(creditCardForm, user.getUsername());
        }

        this.orderService.placeOrder(orderDto, user);
    }

    private void addCard(CreditCardForm creditCardForm, String username) {
        this.creditCardService.addCreditCard(creditCardForm, username);
    }

}
package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.service.CreditCardService;
import com.example.shopping.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final OrderService orderService;

    public CreditCardController(CreditCardService creditCardService, OrderService orderService) {
        this.creditCardService = creditCardService;
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public String addCreditCard(CreditCardForm creditCardForm, @AuthenticationPrincipal ApplicationUserDetails userDetails) {
        addCard(creditCardForm, userDetails.getUsername());

        return "redirect:/";
    }

    private void addCard(CreditCardForm creditCardForm, String username) {
        this.creditCardService.addCreditCard(creditCardForm, username);
    }
}

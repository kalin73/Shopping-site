package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.service.CreditCardService;
import com.example.shopping.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.shopping.utils.Constants.BINDING_RESULT_PATH;
import static com.example.shopping.utils.Utils.currentOrder;

// TODO Verify credit card info
@Controller
@RequestMapping("/creditCard")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final OrderService orderService;

    public CreditCardController(CreditCardService creditCardService, OrderService orderService) {
        this.creditCardService = creditCardService;
        this.orderService = orderService;
    }

    @GetMapping("/cardInformation")
    public String cardInformation() {
        return "cardInformation";
    }

    @PostMapping("/add")
    public String addCreditCard(@Validated CreditCardForm creditCardForm,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal ApplicationUserDetails userDetails) {

        this.creditCardService.addCreditCard(creditCardForm, userDetails.getUsername());

        return "redirect:/";
    }

    @PostMapping("/payWithCard")
    public String payWithCard(boolean save,
                              @Validated CreditCardForm creditCardForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal ApplicationUserDetails user) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "creditCardForm", bindingResult)
                    .addFlashAttribute("creditCardForm", creditCardForm);

            return "redirect:/creditCard/cardInformation";
        }

        if (save) {
            this.creditCardService.addCreditCard(creditCardForm, user.getUsername());
        }

        this.orderService.placeOrder(currentOrder, user);

        currentOrder = null;

        return "redirect:/";
    }

    @ModelAttribute(name = "creditCardForm")
    public CreditCardForm creditCardForm() {
        return new CreditCardForm();
    }

}
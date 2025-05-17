package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.service.CreditCardService;
import com.example.shopping.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.example.shopping.utils.Constants.BINDING_RESULT_PATH;
import static com.example.shopping.utils.Utils.currentOrder;

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
    public ModelAndView addCreditCard(@Validated CreditCardForm creditCardForm,
                                      BindingResult bindingResult,
                                      @AuthenticationPrincipal ApplicationUserDetails userDetails,
                                      ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject(BINDING_RESULT_PATH + "creditCardForm", bindingResult);
            modelAndView.addObject("creditCardForm", creditCardForm);
            modelAndView.setViewName("cardInformation");

            return modelAndView;
        }

        this.creditCardService.addCreditCard(creditCardForm, userDetails.getUsername());
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @PostMapping("/payWithCard")
    public ModelAndView payWithCard(boolean save,
                                    @Validated CreditCardForm creditCardForm,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView,
                                    @AuthenticationPrincipal ApplicationUserDetails user) {
        if (bindingResult.hasErrors()) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.addObject(BINDING_RESULT_PATH + "creditCardForm", bindingResult)
                    .addObject("creditCardForm", creditCardForm);
            modelAndView.setViewName("cardInformation");

            return modelAndView;
        }

        if (save) {
            this.creditCardService.addCreditCard(creditCardForm, user.getUsername());
        }

        this.orderService.placeOrder(currentOrder, user);

        currentOrder = null;

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @ModelAttribute(name = "creditCardForm")
    public CreditCardForm creditCardForm() {
        return new CreditCardForm();
    }

}
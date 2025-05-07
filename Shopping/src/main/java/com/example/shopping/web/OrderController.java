package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.CreditCardForm;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.dto.ShoppingCartDto;
import com.example.shopping.model.enums.PaymentMethod;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ShoppingCartService;
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
@RequestMapping("/order")
public class OrderController {
    private final ShoppingCartService shoppingCartService;

    private final OrderService orderService;

    public OrderController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public ModelAndView getCheckoutPage(@AuthenticationPrincipal ApplicationUserDetails applicationUserDetails, ModelAndView modelAndView) {
        ShoppingCartDto shoppingCart = this.shoppingCartService.getShoppingCart(applicationUserDetails.getUsername());

        if (shoppingCart.getCartItems().isEmpty()) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("index");

            return modelAndView;
        }

        modelAndView.setViewName("orderCheckout");

        return modelAndView;
    }

    @PostMapping("/placeOrder")
    public ModelAndView placeOrder(@AuthenticationPrincipal ApplicationUserDetails client,
                                   @Validated OrderDto order,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        currentOrder = order;

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("order", order)
                    .addObject(BINDING_RESULT_PATH + "order", bindingResult);

            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("orderCheckout");

            return modelAndView;
        }

        if (order.getPaymentMethod().equals(PaymentMethod.CARD.name())) {
            this.shoppingCartService.loadShoppingCart(modelAndView, client);
            modelAndView.addObject("creditCardForm", new CreditCardForm());
            modelAndView.setStatus(HttpStatus.valueOf(302));
            modelAndView.setViewName("cardInformation");

            return modelAndView;
        }

        this.orderService.placeOrder(order, client);

        currentOrder = null;

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @ModelAttribute(name = "order")
    public OrderDto order() {
        return new OrderDto();
    }

}
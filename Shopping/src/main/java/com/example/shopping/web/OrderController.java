package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.enums.PaymentMethod;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ShoppingCartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String placeOrder() {

        return "orderCheckout";
    }

    @PostMapping("/placeOrder")
    public ModelAndView placeOrder(@AuthenticationPrincipal ApplicationUserDetails client,
                                   OrderDto orderDto,
                                   ModelAndView modelAndView) {
        currentOrder = orderDto;

        if (orderDto.getPaymentMethod().equals(PaymentMethod.CARD.name())) {
            this.shoppingCartService.loadShoppingCart(modelAndView, client);
            modelAndView.setViewName("cardInformation");

            return modelAndView;
        }

        this.orderService.placeOrder(orderDto, client);

        currentOrder = null;

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

}
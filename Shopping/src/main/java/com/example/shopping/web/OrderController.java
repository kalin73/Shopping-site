package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.enums.PaymentMethod;
import com.example.shopping.service.OrderService;
import com.example.shopping.service.ShoppingCartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String placeOrder() {

        return "orderCheckout";
    }

    @PostMapping("/placeOrder")
    public ModelAndView placeOrder(@AuthenticationPrincipal ApplicationUserDetails client,
                                   @Validated OrderDto order,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   ModelAndView modelAndView) {
        currentOrder = order;

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("order", order)
                    .addFlashAttribute(BINDING_RESULT_PATH + "order", bindingResult);

            modelAndView.setViewName("redirect:/order/checkout");

            return modelAndView;
        }

        if (order.getPaymentMethod().equals(PaymentMethod.CARD.name())) {
            this.shoppingCartService.loadShoppingCart(modelAndView, client);
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
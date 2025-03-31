package com.example.shopping.web;

import com.example.shopping.model.dto.AddressFormDto;
import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.example.shopping.utils.Constants.BINDING_RESULT_PATH;

@Controller
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/billingAddress")
    public String getOrderAddressForm() {
        return "addressForm";
    }

    @PostMapping("/billingAddress")
    public ModelAndView addOrderAddress(@Validated AddressFormDto addressFrom,
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal ApplicationUserDetails user,
                                        ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("addressForm", addressFrom);
            modelAndView.addObject(BINDING_RESULT_PATH + "addressForm", bindingResult);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("addressForm");

            return modelAndView;
        }

        this.addressService.addAddress(addressFrom, user.getUsername());
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @ModelAttribute(name = "addressForm")
    public AddressFormDto getAddressForm() {
        return new AddressFormDto();
    }
}

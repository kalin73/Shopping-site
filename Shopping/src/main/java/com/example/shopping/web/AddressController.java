package com.example.shopping.web;

import com.example.shopping.model.dto.AddressFormDto;
import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.AddressService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String addOrderAddress(AddressFormDto address, @AuthenticationPrincipal ApplicationUserDetails user) {
        this.addressService.addAddress(address, user.getUsername());

        return "redirect:/";
    }
}

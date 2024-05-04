package com.example.shopping.web;

import com.example.shopping.model.dto.AddressFormDto;
import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.service.AddressService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddressController {
    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/billingAddress")
    public String getOrderAddressForm() {
        return "addressForm";
    }

    @PostMapping("/billingAddress")
    public String addOrderAddress(@Validated AddressFormDto addressFrom,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal ApplicationUserDetails user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addressForm", addressFrom).
                    addFlashAttribute(BINDING_RESULT_PATH + "addressFrom", bindingResult);

            return "redirect:/billingAddress";
        }

        this.addressService.addAddress(addressFrom, user.getUsername());

        return "redirect:/";
    }

    @ModelAttribute(name = "addressForm")
    public AddressFormDto getAddressForm() {
        return new AddressFormDto();
    }
}

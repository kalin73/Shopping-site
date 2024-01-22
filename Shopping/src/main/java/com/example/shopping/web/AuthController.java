package com.example.shopping.web;

import com.example.shopping.exceptions.ExpiredTokenException;
import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.dto.UserDto;
import com.example.shopping.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.shopping.utils.Utils.IS_VALID;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "Register";
    }

    @PostMapping("/register")
    public String register(@Validated RegisterFormDto registerForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerForm", registerForm)
                    .addFlashAttribute(BINDING_RESULT_PATH + "registerForm", bindingResult);

            return "redirect:/auth/register";
        }

        this.userService.registerUser(registerForm);

        return "redirect:/";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        this.userService.validateUser(token);

        return "redirect:/";
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public String expiredToken() {
        return "VerificationPage";
    }

    @PostMapping("/sendVerification")
    public String sendVerificationEmail(String email) {
        this.userService.sendVerificationEmail(email);

        return "redirect:/";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(String email, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        UserDto user = this.userService.getUserByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute("invalidEmail", true);
            modelAndView.setViewName("redirect:/auth/forgotPassword");

            return "redirect:/auth/forgotPassword";
        }
        redirectAttributes.addFlashAttribute("validEmail", true);

        userService.sendConfirmationEmail(user);

        return "redirect:/auth/forgotPassword";
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("token") String token, ModelAndView modelAndView) {
        String userEmail = this.userService.verifyToken(token);

        modelAndView.addObject("email", userEmail);
        modelAndView.setViewName("changePassword");

        return modelAndView;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(String email, String password, String confirmPassword, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        if (password.equals(confirmPassword)) {
            this.userService.changePassword(email, password);
            modelAndView.setViewName("redirect:/");

            return modelAndView;
        }
        redirectAttributes.addFlashAttribute("wrongPass", true);
        modelAndView.setViewName("redirect:/auth/changePassword");

        return modelAndView;
    }

    @PostMapping("/login-error")
    public String loginError(RedirectAttributes redirectAttributes) {
        if (!IS_VALID) {
            IS_VALID = true;
            redirectAttributes.addFlashAttribute("isNotValid", true);
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("bad_credentials", true);
        return "redirect:/";
    }

    @ModelAttribute(name = "registerForm")
    public RegisterFormDto registerForm() {
        return new RegisterFormDto();
    }
}
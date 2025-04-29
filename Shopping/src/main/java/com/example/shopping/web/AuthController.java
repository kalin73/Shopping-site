package com.example.shopping.web;

import com.example.shopping.exceptions.ExpiredTokenException;
import com.example.shopping.model.dto.RegisterFormDto;
import com.example.shopping.model.dto.UserDto;
import com.example.shopping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.example.shopping.utils.Constants.BINDING_RESULT_PATH;
import static com.example.shopping.utils.Utils.IS_VALID;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "Register";
    }

    @PostMapping("/register")
    public ModelAndView register(@Validated RegisterFormDto registerForm, BindingResult bindingResult,
                                 ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("registerForm", registerForm)
                    .addObject(BINDING_RESULT_PATH + "registerForm", bindingResult);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("Register");

            return modelAndView;
        }

        this.userService.registerUser(registerForm);
        modelAndView.setViewName("redirect:/");

        return modelAndView;
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
    public ModelAndView forgotPassword(String email, ModelAndView modelAndView) {
        UserDto user = this.userService.getUserByEmail(email);

        if (user == null) {
            modelAndView.addObject("invalidEmail", true);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName("forgotPassword");

            return modelAndView;
        }
        modelAndView.addObject("validEmail", true);
        modelAndView.setViewName("forgotPassword");

        userService.sendConfirmationEmail(user);

        return modelAndView;
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("token") String token, ModelAndView modelAndView) {
        String userEmail = this.userService.verifyToken(token);

        modelAndView.addObject("email", userEmail);
        modelAndView.setViewName("changePassword");

        return modelAndView;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(String email, String password, String confirmPassword, ModelAndView modelAndView) {
        if (password.equals(confirmPassword)) {
            this.userService.changePassword(email, password);
            modelAndView.setViewName("index");

            return modelAndView;
        }
        modelAndView.addObject("wrongPass", true);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName("changePassword");

        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView loginError(ModelAndView modelAndView) {
        if (!IS_VALID) {
            modelAndView.addObject("isNotValid", true);
            modelAndView.setStatus(HttpStatus.UNAUTHORIZED);
            modelAndView.setViewName("index");

            return modelAndView;
        }

        modelAndView.addObject("bad_credentials", true);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @ModelAttribute(name = "registerForm")
    public RegisterFormDto registerForm() {
        return new RegisterFormDto();
    }
}
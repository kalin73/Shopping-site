package com.example.shopping.web;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.ProductViewDto;
import com.example.shopping.service.ProductService;
import com.example.shopping.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.example.shopping.utils.Utils.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final HttpServletRequest request;

    public ProductsController(ProductService productService, ShoppingCartService shoppingCartService, HttpServletRequest request) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.request = request;
    }

    @GetMapping("/search")
    public ModelAndView getSearchResult(String filter, ModelAndView modelAndView,
                                        @AuthenticationPrincipal ApplicationUserDetails user) {
        List<ProductViewDto> products = this.productService.search(filter);

        this.addingToView(modelAndView, products);

        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getProducts(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user,
                                    @PathVariable(name = "id") Long id) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        lastUrl = request.getRequestURI();

        this.addingToView(modelAndView, id);

        return modelAndView;
    }

    @GetMapping("/info/{id}")
    public ModelAndView getProduct(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user,
                                   @PathVariable(name = "id") Long id) {
        this.shoppingCartService.loadShoppingCart(modelAndView, user);

        lastUrl = request.getRequestURI();

        modelAndView.setViewName("productInfo");
        modelAndView.addObject("id", id);

        return modelAndView;
    }

    private void addingToView(ModelAndView modelAndView, Long id) {
        modelAndView.addObject("id", id);
        modelAndView.setViewName("productsPage");
    }

    private void addingToView(ModelAndView modelAndView, List<ProductViewDto> products) {
        modelAndView.addObject("products", products);
        modelAndView.setViewName("searchPage");
    }
}

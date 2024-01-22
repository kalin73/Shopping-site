package com.example.shopping.service;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.ShoppingCartDto;
import com.example.shopping.model.dto.ShoppingCartItemDto;
import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.ShoppingCartEntity;
import com.example.shopping.model.entity.ShoppingItemEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.ShoppingCartRepository;
import com.example.shopping.repository.ShoppingItemRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final ProductRepository productRepository;
    private final ShoppingItemService shoppingItemService;

    public ShoppingCartService(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository,
                               ProductRepository productRepository, ShoppingItemRepository shoppingItemRepository, ShoppingItemService shoppingItemService) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.productRepository = productRepository;
        this.shoppingItemService = shoppingItemService;
    }

    public Long addToCart(Long id, String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        ShoppingCartEntity cart = shoppingCartRepository.findByUser(user).orElse(new ShoppingCartEntity(user));

        cart = shoppingCartRepository.save(cart);

        ProductEntity product = productRepository.findById(id).get();

        ShoppingItemEntity shoppingItem = this.shoppingItemRepository.findAllByProduct(product).orElse(null);


        if (shoppingItem == null && product.getQuantity() > 0) {
            shoppingItem = new ShoppingItemEntity();
            shoppingItem.setProduct(product).setCart(cart).setQuantity(1);

        } else if (shoppingItem.getCart().getId().equals(cart.getId()) && shoppingItem.getQuantity() < product.getQuantity()) {
            shoppingItem.setQuantity(shoppingItem.getQuantity() + 1);

        }
        shoppingItemRepository.save(shoppingItem);


        return product.getCategory().getId();
    }

    @Transactional
    public void deleteCart(String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        this.shoppingCartRepository.deleteByUser(user);
        this.shoppingItemService.refreshItems();

    }

    public void loadShoppingCart(ModelAndView modelAndView, @AuthenticationPrincipal ApplicationUserDetails user) {
        List<ShoppingCartItemDto> cartItems = getItems(user);

        BigDecimal total = new BigDecimal(0);

        for (ShoppingCartItemDto item : cartItems) {
            total = total.add(item.getAmount());
        }

        modelAndView.addObject("total", total);
        modelAndView.addObject("cartItems", cartItems);
    }

    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal ApplicationUserDetails user) {
        List<ShoppingCartItemDto> cartItems = getItems(user);

        BigDecimal total = new BigDecimal(0);

        for (ShoppingCartItemDto item : cartItems) {
            total = total.add(item.getAmount());
        }

        return new ShoppingCartDto(cartItems, total);
    }

    private List<ShoppingCartItemDto> getItems(@AuthenticationPrincipal ApplicationUserDetails user) {
        return user != null ? shoppingItemService.getAllItemsByUser(user.getUsername()) : new ArrayList<>();

    }
}

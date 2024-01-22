package com.example.shopping.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.shopping.repository.*;
import org.springframework.stereotype.Service;

import com.example.shopping.model.entity.OrderEntity;
import com.example.shopping.model.entity.OrderItemEntity;
import com.example.shopping.model.entity.ShoppingCartEntity;
import com.example.shopping.model.entity.ShoppingItemEntity;
import com.example.shopping.model.entity.UserEntity;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository,
                        UserRepository userRepository, OrderItemRepository orderItemRepository,
                        ShoppingCartService shoppingCartService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
        this.productRepository = productRepository;
    }

    @Transactional
    public void placeOrder(String email) {
        UserEntity user = this.userRepository.findByEmail(email).get();

        ShoppingCartEntity cart = this.shoppingCartRepository.findByUser(user).orElse(null);

        BigDecimal total = calculateTotalCost(cart);

        OrderEntity order = new OrderEntity();
        order.setCustomer(user).setDate(LocalDateTime.now()).setOrderCost(total);

        order = this.orderRepository.save(order);

        List<OrderItemEntity> items = new ArrayList<>();

        for (ShoppingItemEntity item : cart.getItems()) {
            items.add(new OrderItemEntity(item.getProduct(), order));
        }

        this.orderItemRepository.saveAll(items);

        reduceQuantity(cart);

        this.shoppingCartService.deleteCart(email);

    }

    private void reduceQuantity(ShoppingCartEntity cart) {
        cart.getItems()
                .forEach(item -> {
                    if (item.getProduct().getQuantity() > 0) {
                        this.productRepository.updateQuantity(item.getQuantity(), item.getProduct().getProductName());
                    }
                });
    }

    private BigDecimal calculateTotalCost(ShoppingCartEntity cart) {
        BigDecimal total = new BigDecimal("0.0");

        for (ShoppingItemEntity item : cart.getItems()) {
            total = total.add(BigDecimal.valueOf(item.getQuantity()).multiply(item.getProduct().getPrice()));
        }

        return total;
    }
}

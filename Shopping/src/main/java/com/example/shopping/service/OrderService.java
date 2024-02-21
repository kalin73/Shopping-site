package com.example.shopping.service;

import com.example.shopping.model.dto.ApplicationUserDetails;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.entity.*;
import com.example.shopping.model.enums.PaymentMethod;
import com.example.shopping.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public void placeOrder(OrderDto orderDto, ApplicationUserDetails client) {
        final UserEntity user = this.userRepository.findByEmail(client.getUsername()).orElseThrow();

        final ShoppingCartEntity cart = this.shoppingCartRepository.findByUser(user).orElseThrow();

        final BigDecimal total = calculateTotalCost(cart);

        OrderEntity order = new OrderEntity();
        order.setCustomer(user)
                .setDate(LocalDateTime.now())
                .setOrderCost(total)
                .setPhoneNumber(orderDto.getPhoneNumber())
                .setShippingAddress(orderDto.getShippingAddress())
                .setPaymentMethod(PaymentMethod.valueOf(orderDto.getPaymentMethod()));

        order = this.orderRepository.save(order);

        final List<OrderItemEntity> items = new ArrayList<>();

        for (ShoppingItemEntity item : cart.getItems()) {
            items.add(new OrderItemEntity(item.getProduct(), order));
        }

        this.orderItemRepository.saveAll(items);

        reduceQuantity(cart);

        this.shoppingCartService.deleteCart(client.getUsername());

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

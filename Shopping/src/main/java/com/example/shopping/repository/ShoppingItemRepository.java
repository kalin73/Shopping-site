package com.example.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.ShoppingCartEntity;
import com.example.shopping.model.entity.ShoppingItemEntity;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItemEntity, Long> {
	Optional<List<ShoppingItemEntity>> findAllByCart(ShoppingCartEntity cart);
	Optional<ShoppingItemEntity> findAllByProduct(ProductEntity product);
}
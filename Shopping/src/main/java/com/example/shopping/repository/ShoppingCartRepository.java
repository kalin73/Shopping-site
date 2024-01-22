package com.example.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.entity.ShoppingCartEntity;
import com.example.shopping.model.entity.UserEntity;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
	Optional<ShoppingCartEntity> findByUser(UserEntity user);

	void deleteByUser(UserEntity user);
}

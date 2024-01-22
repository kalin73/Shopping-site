package com.example.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.productName LIKE %:filter%")
    Optional<List<ProductEntity>> search(String filter);

    Optional<List<ProductEntity>> findAllByCategory(CategoryEntity category);

    Optional<List<ProductEntity>> findAllByCategoryAndQuantityGreaterThan(CategoryEntity category, int quantity);

    @Query("UPDATE ProductEntity p SET p.quantity=p.quantity - :amount WHERE p.productName=:name")
    @Modifying
    void updateQuantity(int amount, String name);

}

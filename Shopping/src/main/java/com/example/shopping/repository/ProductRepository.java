package com.example.shopping.repository;

import com.example.shopping.model.entity.CategoryEntity;
import com.example.shopping.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    String SEARCH_QUERY = "SELECT p FROM ProductEntity p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
            "OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :filter, '%'))";

    @Query(SEARCH_QUERY)
    Optional<List<ProductEntity>> search(String filter);

    Optional<List<ProductEntity>> findAllByCategory(CategoryEntity category);

    Optional<List<ProductEntity>> findAllByCategoryAndQuantityGreaterThan(CategoryEntity category, int quantity);

    @Query("UPDATE ProductEntity p SET p.quantity=p.quantity - :amount WHERE p.productName=:name")
    @Modifying
    @Transactional
    void updateQuantity(int amount, String name);

}

package com.example.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.SpecificationsEntity;

public interface SpecificationsRepository extends JpaRepository<SpecificationsEntity, Long> {
	List<SpecificationsEntity> findAllByProduct(ProductEntity productEntity);

	List<SpecificationsEntity> findAllByProductId(Long id);
}

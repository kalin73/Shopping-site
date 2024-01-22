package com.example.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}

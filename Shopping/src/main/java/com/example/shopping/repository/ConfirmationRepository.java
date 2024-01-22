package com.example.shopping.repository;

import com.example.shopping.model.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    ConfirmationEntity findByToken(String token);
}

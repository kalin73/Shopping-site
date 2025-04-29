package com.example.shopping.repository;

import com.example.shopping.model.entity.ConfirmationEntity;
import com.example.shopping.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    ConfirmationEntity findByToken(String token);
    List<ConfirmationEntity> findAllByUser(UserEntity user);
}

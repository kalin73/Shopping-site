package com.example.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
	Boolean existsByEmail(String email);

	@Modifying
	@Query("UPDATE UserEntity u SET u.isEnabled=:isEnabled WHERE u.email=:email")
	void updateAccountStatus(boolean isEnabled, String email);
}

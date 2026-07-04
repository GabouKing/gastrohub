package com.example.gastrohub.infra.persistence.repository;

import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findById(Long userId);
    Optional<UserJpaEntity> findByEmail(String userEmail);
    List<UserJpaEntity> findAll();
}

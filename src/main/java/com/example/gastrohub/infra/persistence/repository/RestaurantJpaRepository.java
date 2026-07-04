package com.example.gastrohub.infra.persistence.repository;

import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Long> {
    Optional<RestaurantJpaEntity> findByName(String name);
    boolean existsByName(String name);
}

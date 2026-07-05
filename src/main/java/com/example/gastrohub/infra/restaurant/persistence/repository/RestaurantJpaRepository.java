package com.example.gastrohub.infra.restaurant.persistence.repository;

import com.example.gastrohub.infra.restaurant.persistence.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Long> {
    Optional<RestaurantJpaEntity> findById(Long id);
    Optional<RestaurantJpaEntity> findByName(String name);
    List<RestaurantJpaEntity> findByNameContaining(String name);
    List<RestaurantJpaEntity> findAll();
    Boolean existsByName(String name);
}

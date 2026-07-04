package com.example.gastrohub.infra.presistence.repository;

import com.example.gastrohub.infra.presistence.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Long> {
}

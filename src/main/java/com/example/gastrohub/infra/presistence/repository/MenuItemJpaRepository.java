package com.example.gastrohub.infra.presistence.repository;

import com.example.gastrohub.infra.presistence.entity.MenuItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemJpaEntity, Long> {
    List<MenuItemJpaEntity> findByRestaurantId(Long restaurantId);
}

package com.example.gastrohub.infra.persistence.mapper;

import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPersistenceMapper {
    public RestaurantJpaEntity toEntity(Restaurant restaurant, UserJpaEntity user) {
        return RestaurantJpaEntity.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType())
                .openingHours(restaurant.getOpeningHours())
                .user(user)
                .build();
    }

    public Restaurant toDomain(RestaurantJpaEntity entity) {
        return Restaurant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .cuisineType(entity.getCuisineType())
                .openingHours(entity.getOpeningHours())
                .userId(entity.getUser().getId())

                .build();
    }
}

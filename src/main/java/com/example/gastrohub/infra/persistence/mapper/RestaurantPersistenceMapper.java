package com.example.gastrohub.infra.persistence.mapper;

import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPersistenceMapper {

    public RestaurantJpaEntity toEntity(Restaurant restaurant) {
        return new RestaurantJpaEntity(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType().name(),
                restaurant.getOpeningHours(),
                restaurant.getUserId()
        );
    }

    public Restaurant toDomain(RestaurantJpaEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                CuisineType.valueOf(entity.getCuisineType()),
                entity.getOpeningHours(),
                entity.getOwnerId()
        );
    }
}
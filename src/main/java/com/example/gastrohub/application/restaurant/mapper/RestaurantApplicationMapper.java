package com.example.gastrohub.application.restaurant.mapper;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.domain.restaurant.Restaurant;

public class RestaurantApplicationMapper {

    public static Restaurant toDomain(CreateRestaurantInput createRestaurantInput) {
        return Restaurant.builder()
                .name(createRestaurantInput.getName())
                .address(createRestaurantInput.getAddress())
                .cuisineType(createRestaurantInput.getCuisineType())
                .openingHours(createRestaurantInput.getOpeningHours())
                .userId(createRestaurantInput.getUserId())
                .build();
    }

    public static Restaurant toDomain(UpdateRestaurantInput updateRestaurantInput) {
        return Restaurant.builder()
                .id(updateRestaurantInput.getId())
                .name(updateRestaurantInput.getName())
                .address(updateRestaurantInput.getAddress())
                .cuisineType(updateRestaurantInput.getCuisineType())
                .openingHours(updateRestaurantInput.getOpeningHours())
                .userId(updateRestaurantInput.getUserId())
                .build();
    }

    public static RestaurantOutput toOutput(Restaurant restaurant) {
        return RestaurantOutput.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType())
                .openingHours(restaurant.getOpeningHours())
                .userId(restaurant.getUserId())
                .build();
    }
}


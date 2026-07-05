package com.example.gastrohub.application.restaurant.mapper;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.domain.restaurant.Restaurant;

public class RestaurantApplicationMapper {

    public static Restaurant toDomain(CreateRestaurantInput createRestaurantInput) {
        return new Restaurant(

                createRestaurantInput.getName(),
                createRestaurantInput.getAddress(),
                createRestaurantInput.getCuisineType(),
                createRestaurantInput.getOpeningHours(),
                createRestaurantInput.getUserId()
                );
    }

    public static Restaurant toDomain(UpdateRestaurantInput updateRestaurantInput) {
        return new Restaurant(
                updateRestaurantInput.getName(),
                updateRestaurantInput.getAddress(),
                updateRestaurantInput.getCuisineType(),
                updateRestaurantInput.getOpeningHours(),
                updateRestaurantInput.getUserId()
        );
    }

    public static RestaurantOutput toOutput(Restaurant restaurant) {
        return new RestaurantOutput(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getUserId()
        );
    }
}


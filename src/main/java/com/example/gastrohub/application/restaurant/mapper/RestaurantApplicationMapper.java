package com.example.gastrohub.application.restaurant.mapper;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantRequest;
import com.example.gastrohub.application.restaurant.dto.RestaurantResponse;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantRequest;
import com.example.gastrohub.domain.restaurant.Restaurant;

public class RestaurantApplicationMapper {

    public static Restaurant toDomain(CreateRestaurantRequest createRestaurantRequest) {
        return new Restaurant(

                createRestaurantRequest.getName(),
                createRestaurantRequest.getAddress(),
                createRestaurantRequest.getCuisineType(),
                createRestaurantRequest.getOpeningHours(),
                createRestaurantRequest.getUserId()
                );
    }

    public static Restaurant toDomain(UpdateRestaurantRequest updateRestaurantRequest) {
        return new Restaurant(
                updateRestaurantRequest.getId(),
                updateRestaurantRequest.getName(),
                updateRestaurantRequest.getAddress(),
                updateRestaurantRequest.getCuisineType(),
                updateRestaurantRequest.getOpeningHours(),
                updateRestaurantRequest.getUserId()
        );
    }

    public static RestaurantResponse toOutput(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getOpeningHours(),
                restaurant.getUserId()
        );
    }
}


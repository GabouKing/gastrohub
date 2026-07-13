package com.example.gastrohub.presentation.restaurant.mapper;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.presentation.restaurant.request.CreateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.request.UpdateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.response.RestaurantResponse;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPresentationMapper {

    public RestaurantPresentationMapper() {
    }

    public static CreateRestaurantInput toInput(CreateRestaurantRequest request) {
        return CreateRestaurantInput.builder()
                .name(request.getName())
                .address(request.getAddress())
                .cuisineType(request.getCuisineType())
                .openingHours(request.getOpeningHours())
                .userId(request.getUserId())
                .build();
    }

    public static UpdateRestaurantInput toInput(UpdateRestaurantRequest request) {
        return UpdateRestaurantInput.builder()
                .name(request.getName())
                .address(request.getAddress())
                .cuisineType(request.getCuisineType())
                .openingHours(request.getOpeningHours())
                .userId(request.getUserId())
                .build();
    }

    public static RestaurantResponse toResponse(RestaurantOutput output) {
        return new RestaurantResponse(
                output.getId(),
                output.getName(),
                output.getAddress(),
                output.getCuisineType(),
                output.getOpeningHours(),
                output.getUserId()
        );
    }



    public static RestaurantResponse toResponse(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType())
                .openingHours(restaurant.getOpeningHours())
                .build();
    }
}

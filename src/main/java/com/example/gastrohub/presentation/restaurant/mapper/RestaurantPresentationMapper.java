package com.example.gastrohub.presentation.restaurant.mapper;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.presentation.restaurant.request.CreateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.request.UpdateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.response.RestaurantResponse;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPresentationMapper {

    public RestaurantPresentationMapper() {
    }

    public static CreateRestaurantInput toInput(CreateRestaurantRequest request) {
        return new CreateRestaurantInput(
                request.getName(),
                request.getAddress(),
                request.getCuisineType(),
                request.getOpeningHours(),
                request.getUserId()
        );
    }

    public static UpdateRestaurantInput toInput(UpdateRestaurantRequest request){
        return new UpdateRestaurantInput(
                request.getName(),
                request.getAddress(),
                request.getCuisineType(),
                request.getOpeningHours(),
                request.getUserId()
        );
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
}

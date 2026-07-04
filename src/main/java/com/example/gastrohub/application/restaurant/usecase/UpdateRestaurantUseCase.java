package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantResponse;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantRequest;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public UpdateRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public RestaurantResponse execute(UpdateRestaurantRequest updateRestaurantRequest){
        Restaurant restaurant = restaurantGateway.findById(updateRestaurantRequest.getId())
                .orElseThrow(() -> new RestaurantNotFound(updateRestaurantRequest.getId()));

        restaurant.update(
                updateRestaurantRequest.getName(),
                updateRestaurantRequest.getAddress(),
                updateRestaurantRequest.getCuisineType(),
                updateRestaurantRequest.getOpeningHours()
        );

        Restaurant updatedRestaurant = restaurantGateway.save(restaurant);

        return RestaurantApplicationMapper.toOutput(updatedRestaurant);
    }
}

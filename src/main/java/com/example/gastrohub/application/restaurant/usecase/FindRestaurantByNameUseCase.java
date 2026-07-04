package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantResponse;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.springframework.stereotype.Service;

@Service
public class FindRestaurantByNameUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByNameUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public RestaurantResponse execute(String name){
        Restaurant restaurant = restaurantGateway.findByName(name)
                .orElseThrow(() -> new RestaurantNotFound("Restaurant not found"));

        return RestaurantApplicationMapper.toOutput(restaurant);
    }
}

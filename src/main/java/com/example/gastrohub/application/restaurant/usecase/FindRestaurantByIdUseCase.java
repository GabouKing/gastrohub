package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.springframework.stereotype.Service;

@Service
public class FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public RestaurantOutput execute(Long id){
        Restaurant restaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFound("Restaurant not found"));

        return RestaurantApplicationMapper.toOutput(restaurant);
    }
}

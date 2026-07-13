package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException ;
import org.springframework.stereotype.Service;

@Service
public class FindRestaurantByNameUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByNameUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public RestaurantOutput execute(String name){
        Restaurant restaurant = restaurantGateway.findByName(name)
                .orElseThrow(() -> new RestaurantNotFoundException ("Restaurant not found"));

        return RestaurantApplicationMapper.toOutput(restaurant);
    }
}

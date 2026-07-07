package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRestaurantByNameContaining {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByNameContaining(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<RestaurantOutput> execute(String name) {

        List<Restaurant> restaurants = restaurantGateway.findByNameContaining(name);

        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException ("Restaurant not found");
        }

        return restaurants.stream()
                .map(RestaurantApplicationMapper::toOutput)
                .toList();
    }
}

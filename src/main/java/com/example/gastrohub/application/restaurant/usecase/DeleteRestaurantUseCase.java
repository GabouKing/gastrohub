package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.springframework.stereotype.Service;

@Service
public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;


    public DeleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public void execute(Long id) {

        if (!restaurantGateway.existsById(id)){
            throw new RestaurantNotFound(id);
        }

        restaurantGateway.delete(id);
    }
}

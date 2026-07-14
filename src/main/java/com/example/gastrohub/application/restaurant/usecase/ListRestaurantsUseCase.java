package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;

    public ListRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<RestaurantOutput> execute() {
        return restaurantGateway.findAll().stream().map(RestaurantApplicationMapper::toOutput).toList();
    }
}

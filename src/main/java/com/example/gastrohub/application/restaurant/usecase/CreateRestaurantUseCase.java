package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantRequest;
import com.example.gastrohub.application.restaurant.dto.RestaurantResponse;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;


    public CreateRestaurantUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public RestaurantResponse execute(CreateRestaurantRequest createRestaurantRequest) {
        if (!userGateway.existsById(createRestaurantRequest.getUserId())) {
            throw new UserNotFound(createRestaurantRequest.getUserId());
        }

        if (restaurantGateway.existsByName(createRestaurantRequest.getName())){
            throw new RestaurantNotFound("The restaurant already exists.");
        }

        var restaurant = RestaurantApplicationMapper.toDomain(createRestaurantRequest);
        var savedRestaurant = restaurantGateway.save(restaurant);

        return RestaurantApplicationMapper.toOutput(savedRestaurant);
    }
}

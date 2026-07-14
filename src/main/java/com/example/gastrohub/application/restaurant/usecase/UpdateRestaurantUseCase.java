package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException ;
import org.springframework.stereotype.Service;

@Service
public class UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public UpdateRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public RestaurantOutput execute(Long id ,UpdateRestaurantInput updateRestaurantInput){
        Restaurant restaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException ("Restaurant not found."));


        restaurant.update(
                updateRestaurantInput.getName(),
                updateRestaurantInput.getAddress(),
                updateRestaurantInput.getCuisineType(),
                updateRestaurantInput.getOpeningHours()
        );

        Restaurant updatedRestaurant = restaurantGateway.save(restaurant);

        return RestaurantApplicationMapper.toOutput(updatedRestaurant);
    }
}

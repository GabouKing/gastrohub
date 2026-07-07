package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantAlreadyExistsException;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException ;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.UserRole;
import com.example.gastrohub.domain.user.exception.UserNotAllowedException;
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

    public RestaurantOutput execute(CreateRestaurantInput createRestaurantInput) {

        User user = userGateway.findById(createRestaurantInput.getUserId())
                .orElseThrow(() -> new UserNotFound(createRestaurantInput.getUserId()));

        if (user.getRole() != UserRole.USER_OWNER) {
            throw new UserNotAllowedException(
                    "Only users with USER_OWNER role can create restaurants."
            );
        }

        if (!userGateway.existsById(createRestaurantInput.getUserId())) {
            throw new UserNotFound(createRestaurantInput.getUserId());
        }

        if (restaurantGateway.existsByName(createRestaurantInput.getName())){
            throw new RestaurantNotFoundException ("The restaurant already exists.");
        }

        if (restaurantGateway.existsByName(createRestaurantInput.getName())){
            throw new RestaurantAlreadyExistsException("The restaurant already exists.");
        }

        var restaurant = RestaurantApplicationMapper.toDomain(createRestaurantInput);
        var savedRestaurant = restaurantGateway.save(restaurant);

        return RestaurantApplicationMapper.toOutput(savedRestaurant);
    }
}

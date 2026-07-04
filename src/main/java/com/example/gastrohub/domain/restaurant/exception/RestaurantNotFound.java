package com.example.gastrohub.domain.restaurant.exception;

import lombok.Getter;

@Getter
public class RestaurantNotFound extends RuntimeException {
    private final Long restaurantId;

    public RestaurantNotFound(Long restaurantId) {
        super("Restaurant not found: " + restaurantId);
        this.restaurantId = restaurantId;
    }

    public RestaurantNotFound(String message) {
        super(message);
        this.restaurantId = null;
    }
}

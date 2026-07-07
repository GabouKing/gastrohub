package com.example.gastrohub.domain.restaurant.exception;

public class RestaurantNotFoundException  extends RuntimeException {
    public RestaurantNotFoundException (String message) {
        super(message);
    }
}

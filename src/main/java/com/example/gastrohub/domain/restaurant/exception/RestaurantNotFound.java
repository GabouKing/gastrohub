package com.example.gastrohub.domain.restaurant.exception;

public class RestaurantNotFound extends RuntimeException {
    public RestaurantNotFound(String message) {
        super(message);
    }
}

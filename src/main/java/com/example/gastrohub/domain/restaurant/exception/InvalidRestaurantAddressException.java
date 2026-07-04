package com.example.gastrohub.domain.restaurant.exception;

public class InvalidRestaurantAddressException extends RuntimeException {
    public InvalidRestaurantAddressException(String message) {
        super(message);
    }
}

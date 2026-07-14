package com.example.gastrohub.domain.restaurant.exception;

public class InvalidOpeningHoursException extends RuntimeException {
    public InvalidOpeningHoursException(String message) {
        super(message);
    }
}

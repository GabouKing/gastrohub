package com.example.gastrohub.domain.restaurant.exception;

import lombok.Getter;

@Getter
public class RestaurantNotFoundException  extends RuntimeException {
    private String messageRecieved;
    public RestaurantNotFoundException (String message) {
        super(message);
    }
}

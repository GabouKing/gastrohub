package com.example.gastrohub.domain.restaurant.exception;

public class InvalidRestaurantNameException extends RuntimeException {
  public InvalidRestaurantNameException(String message) {
    super(message);
  }
}

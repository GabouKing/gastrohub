package com.example.gastrohub.domain.restaurant;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.restaurant.exception.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private CuisineType cuisineType;
    private String openingHours;
    private Long userId;

    public Restaurant(
            String name,
            String address,
            CuisineType cuisineType,
            String openingHours,
            Long userId
    ) {

        validateName(name);
        validateAddress(address);
        validateCuisineType(cuisineType);
        validateOpeningHours(openingHours);
        validateUser(userId);

        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.userId = userId;

    }

    public Restaurant(
            Long id,
            String name,
            String address,
            CuisineType cuisineType,
            String openingHours,
            Long userId
    ) {

        validateName(name);
        validateAddress(address);
        validateCuisineType(cuisineType);
        validateOpeningHours(openingHours);
        validateUser(userId);

        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.userId = userId;

    }

    public void update(
            String name,
            String address,
            CuisineType cuisineType,
            String openingHours
    ) {
        validateName(name);
        validateAddress(address);
        validateCuisineType(cuisineType);
        validateOpeningHours(openingHours);

        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
    }


    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidRestaurantNameException("Restaurant name is required.");
        }

        String value = name.trim();

        if (value.length() < 3 || value.length() > 100) {
            throw new InvalidRestaurantNameException("Restaurant name must contain between 3 and 100 characters.");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new InvalidRestaurantAddressException("Address is required.");
        }

        String value = address.trim();

        if (value.length() < 10 || value.length() > 255) {
            throw new InvalidRestaurantAddressException("Address must contain between 10 and 255 characters.");
        }
    }

    private void validateCuisineType(CuisineType cuisineType) {
        if (cuisineType == null) {
            throw new InvalidCuisineTypeException("Cuisine type is required.");
        }
    }

    private static final String OPENING_HOURS_REGEX =
            "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$";

    private void validateOpeningHours(String openingHours) {

        if (openingHours == null || openingHours.isBlank()) {
            throw new InvalidOpeningHoursException("Opening hours are required.");
        }

        if (!openingHours.matches(OPENING_HOURS_REGEX)) {
            throw new InvalidOpeningHoursException(
                    "Opening hours must follow the format HH:mm-HH:mm.");
        }
    }

    private void validateUser(Long userId) {
        if (userId == null) {
            throw new InvalidUserException("User is required.");
        }
    }


}

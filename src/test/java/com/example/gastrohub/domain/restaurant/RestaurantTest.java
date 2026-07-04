package com.example.gastrohub.domain.restaurant;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.restaurant.exception.InvalidCuisineTypeException;
import com.example.gastrohub.domain.restaurant.exception.InvalidRestaurantAddressException;
import com.example.gastrohub.domain.restaurant.exception.InvalidRestaurantNameException;
import com.example.gastrohub.domain.restaurant.exception.InvalidUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class RestaurantTest {
    @Test
    @DisplayName("Should create restaurant when all fields are valid")
    void shouldCreateRestaurant_WhenAllFieldsAreValid() {
        assertDoesNotThrow(() -> new Restaurant(
                1L,
                "Pizza House",
                "123 Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                10L
        ));
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantNameException when name is null")
    void shouldThrowInvalidRestaurantNameException_WhenNameIsNull() {
        assertThrowsExactly(
                InvalidRestaurantNameException.class,
                () -> new Restaurant(
                        1L,
                        null,
                        "123 Main Street",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantNameException when name is blank")
    void shouldThrowInvalidRestaurantNameException_WhenNameIsBlank() {
        assertThrowsExactly(
                InvalidRestaurantNameException.class,
                () -> new Restaurant(
                        1L,
                        "   ",
                        "123 Main Street, Downtown",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantNameException when name is too short")
    void shouldThrowInvalidRestaurantNameException_WhenNameIsTooShort() {
        assertThrowsExactly(
                InvalidRestaurantNameException.class,
                () -> new Restaurant(
                        1L,
                        "AB",
                        "123 Main Street, Downtown",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantNameException when name is too long")
    void shouldThrowInvalidRestaurantNameException_WhenNameIsTooLong() {

        String longName = "A".repeat(101);

        assertThrowsExactly(
                InvalidRestaurantNameException.class,
                () -> new Restaurant(
                        1L,
                        longName,
                        "123 Main Street, Downtown",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantAddressException when address is null")
    void shouldThrowInvalidRestaurantAddressException_WhenAddressIsNull() {
        assertThrowsExactly(
                InvalidRestaurantAddressException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        null,
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantAddressException when address is blank")
    void shouldThrowInvalidRestaurantAddressException_WhenAddressIsBlank() {
        assertThrowsExactly(
                InvalidRestaurantAddressException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        "   ",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantAddressException when address is too short")
    void shouldThrowInvalidRestaurantAddressException_WhenAddressIsTooShort() {
        assertThrowsExactly(
                InvalidRestaurantAddressException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        "Street 1",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidRestaurantAddressException when address is too long")
    void shouldThrowInvalidRestaurantAddressException_WhenAddressIsTooLong() {

        String longAddress = "A".repeat(256);

        assertThrowsExactly(
                InvalidRestaurantAddressException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        longAddress,
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidCuisineTypeException when cuisine type is null")
    void shouldThrowInvalidCuisineTypeException_WhenCuisineTypeIsNull() {
        assertThrowsExactly(
                InvalidCuisineTypeException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        "123 Main Street, Downtown",
                        null,
                        "08:00-22:00",
                        10L
                )
        );
    }

    @Test
    @DisplayName("Should throw InvalidUserException when user id is null")
    void shouldThrowInvalidUserException_WhenUserIdIsNull() {
        assertThrowsExactly(
                InvalidUserException.class,
                () -> new Restaurant(
                        1L,
                        "Bella Italia",
                        "123 Main Street, Downtown",
                        CuisineType.ITALIAN,
                        "08:00-22:00",
                        null
                )
        );
    }
}
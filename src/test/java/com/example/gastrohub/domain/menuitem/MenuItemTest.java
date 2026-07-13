package com.example.gastrohub.domain.menuitem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    @DisplayName("Should create menu item when all fields are valid")
    void shouldCreateMenuItem_WhenAllFieldsAreValid() {
        var item = new MenuItem(
                1L,
                10L,
                "Pizza Margherita",
                "Classic Italian pizza",
                new BigDecimal("29.90"),
                false,
                "/photos/pizza.jpg"
        );

        assertAll(
                () -> assertEquals(1L, item.getId()),
                () -> assertEquals(10L, item.getRestaurantId()),
                () -> assertEquals("Pizza Margherita", item.getName()),
                () -> assertEquals("Classic Italian pizza", item.getDescription()),
                () -> assertEquals(0, new BigDecimal("29.90").compareTo(item.getPrice())),
                () -> assertFalse(item.getAvailableOnlyOnRestaurant()),
                () -> assertEquals("/photos/pizza.jpg", item.getPhotoPath())
        );
    }

    @Test
    @DisplayName("Should update menu item fields")
    void shouldUpdateMenuItemFields() {
        var item = new MenuItem(
                1L,
                10L,
                "Pizza Margherita",
                "Classic Italian pizza",
                new BigDecimal("29.90"),
                false,
                "/photos/pizza.jpg"
        );

        item.update(
                "Pizza Pepperoni",
                "Spicy pepperoni pizza",
                new BigDecimal("34.90"),
                true,
                "/photos/pepperoni.jpg"
        );

        assertAll(
                () -> assertEquals("Pizza Pepperoni", item.getName()),
                () -> assertEquals("Spicy pepperoni pizza", item.getDescription()),
                () -> assertEquals(0, new BigDecimal("34.90").compareTo(item.getPrice())),
                () -> assertTrue(item.getAvailableOnlyOnRestaurant()),
                () -> assertEquals("/photos/pepperoni.jpg", item.getPhotoPath()),
                () -> assertEquals(1L, item.getId()),
                () -> assertEquals(10L, item.getRestaurantId())
        );
    }

    @Test
    @DisplayName("Should create menu item with null optional fields")
    void shouldCreateMenuItem_WithNullOptionalFields() {
        var item = new MenuItem(
                1L,
                10L,
                "Coke",
                null,
                new BigDecimal("5.00"),
                false,
                null
        );

        assertAll(
                () -> assertNull(item.getDescription()),
                () -> assertNull(item.getPhotoPath())
        );
    }
}
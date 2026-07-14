package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRestaurantsUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private ListRestaurantsUseCase listRestaurantsUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() throws Exception {
        restaurant = new Restaurant(
                "Pizza House",
                "123 Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                1L
        );

        setId(restaurant, 1L);
    }

    @Test
    @DisplayName("Should return all restaurants successfully")
    void shouldReturnAllRestaurantsSuccessfully() {
        // Arrange
        when(restaurantGateway.findAll())
                .thenReturn(List.of(restaurant));

        // Act
        List<RestaurantOutput> response = listRestaurantsUseCase.execute();

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> assertEquals(1L, response.get(0).getId()),
                () -> assertEquals("Pizza House", response.get(0).getName()),
                () -> assertEquals("123 Main Street", response.get(0).getAddress()),
                () -> assertEquals(CuisineType.ITALIAN, response.get(0).getCuisineType()),
                () -> assertEquals("08:00-22:00", response.get(0).getOpeningHours()),
                () -> assertEquals(1L, response.get(0).getUserId())
        );

        verify(restaurantGateway).findAll();
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    @DisplayName("Should return empty list when no restaurants exist")
    void shouldReturnEmptyListWhenNoRestaurantsExist() {
        // Arrange
        when(restaurantGateway.findAll())
                .thenReturn(List.of());

        // Act
        List<RestaurantOutput> response = listRestaurantsUseCase.execute();

        // Assert
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(restaurantGateway).findAll();
        verifyNoMoreInteractions(restaurantGateway);
    }

    private void setId(Restaurant restaurant, Long id) throws Exception {
        Field field = Restaurant.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(restaurant, id);
    }
}
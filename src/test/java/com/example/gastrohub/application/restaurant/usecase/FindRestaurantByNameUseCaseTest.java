package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class FindRestaurantByNameUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private FindRestaurantByNameUseCase findRestaurantByNameUseCase;

    private Restaurant restaurant;
    private Long restaurantId;
    private String restaurantName;

    @BeforeEach
    void setUp() {
        restaurantId = 1L;
        restaurantName = "Pizza House";

        restaurant = new Restaurant(
                restaurantId,
                restaurantName,
                "123 Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                10L
        );
    }

    @Test
    @DisplayName("Should return restaurant when restaurant exists")
    void shouldReturnRestaurantWhenRestaurantExists() {
        // Arrange
        when(restaurantGateway.findByName(restaurantName))
                .thenReturn(Optional.of(restaurant));

        // Act
        RestaurantOutput response =
                findRestaurantByNameUseCase.execute(restaurantName);

        // Assert
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(restaurant.getId(), response.getId()),
                () -> assertEquals(restaurant.getName(), response.getName()),
                () -> assertEquals(restaurant.getAddress(), response.getAddress()),
                () -> assertEquals(restaurant.getCuisineType(), response.getCuisineType()),
                () -> assertEquals(restaurant.getOpeningHours(), response.getOpeningHours()),
                () -> assertEquals(restaurant.getUserId(), response.getUserId())
        );

        verify(restaurantGateway).findByName(restaurantName);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFound when restaurant does not exist")
    void shouldThrowRestaurantNotFoundWhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.findByName(restaurantName))
                .thenReturn(Optional.empty());

        // Act
        RestaurantNotFound exception = assertThrows(
                RestaurantNotFound.class,
                () -> findRestaurantByNameUseCase.execute(restaurantName)
        );

        // Assert
        assertEquals(
                "Restaurant not found",
                exception.getMessage()
        );

        verify(restaurantGateway).findByName(restaurantName);
        verifyNoMoreInteractions(restaurantGateway);
    }

}
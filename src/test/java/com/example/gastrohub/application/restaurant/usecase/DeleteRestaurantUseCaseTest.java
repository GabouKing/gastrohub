package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    private Long restaurantId;

    @BeforeEach
    void setUp() {
        restaurantId = 1L;
    }

    @Test
    @DisplayName("Should delete restaurant successfully when restaurant exists")
    void shouldDeleteRestaurantSuccessfullyWhenRestaurantExists() {
        // Arrange
        when(restaurantGateway.existsById(restaurantId))
                .thenReturn(true);

        // Act
        deleteRestaurantUseCase.execute(restaurantId);

        // Assert
        verify(restaurantGateway).existsById(restaurantId);
        verify(restaurantGateway).delete(restaurantId);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFound when restaurant does not exist")
    void shouldThrowRestaurantNotFoundWhenRestaurantDoesNotExist() {
        // Arrange
        when(restaurantGateway.existsById(restaurantId))
                .thenReturn(false);

        // Act
        RestaurantNotFound exception = assertThrows(
                RestaurantNotFound.class,
                () -> deleteRestaurantUseCase.execute(restaurantId)
        );

        // Assert
        assertEquals(
                "Restaurant not found: 1",
                exception.getMessage()
        );

        verify(restaurantGateway).existsById(restaurantId);
        verify(restaurantGateway, never()).delete(anyLong());
        verifyNoMoreInteractions(restaurantGateway);
    }
}
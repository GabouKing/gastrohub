package com.example.gastrohub.application.restaurant.usecase;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.mapper.RestaurantApplicationMapper;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    private CreateRestaurantInput request;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        request = new CreateRestaurantInput(
                "Pizza House",
                "123 Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                1L
        );

        restaurant = RestaurantApplicationMapper.toDomain(request);
    }

    @Test
    @DisplayName("Should create a restaurant successfully when request is valid")
    void shouldCreateRestaurantSuccessfully() {
        // Arrange
        when(userGateway.existsById(request.getUserId()))
                .thenReturn(true);

        when(restaurantGateway.existsByName(request.getName()))
                .thenReturn(false);

        when(restaurantGateway.save(any(Restaurant.class)))
                .thenReturn(restaurant);

        // Act
        RestaurantOutput response = createRestaurantUseCase.execute(request);

        // Assert
        assertNotNull(response);

        ArgumentCaptor<Restaurant> captor =
                ArgumentCaptor.forClass(Restaurant.class);

        verify(userGateway).existsById(request.getUserId());
        verify(restaurantGateway).existsByName(request.getName());
        verify(restaurantGateway).save(captor.capture());

        Restaurant savedRestaurant = captor.getValue();

        assertAll(
                () -> assertEquals(request.getName(), savedRestaurant.getName()),
                () -> assertEquals(request.getAddress(), savedRestaurant.getAddress()),
                () -> assertEquals(request.getCuisineType(), savedRestaurant.getCuisineType()),
                () -> assertEquals(request.getOpeningHours(), savedRestaurant.getOpeningHours()),
                () -> assertEquals(request.getUserId(), savedRestaurant.getUserId())
        );

        verifyNoMoreInteractions(userGateway, restaurantGateway);
    }

    @Test
    @DisplayName("Should throw UserNotFound when owner does not exist")
    void shouldThrowUserNotFoundWhenOwnerDoesNotExist() {
        // Arrange
        when(userGateway.existsById(request.getUserId()))
                .thenReturn(false);

        // Act
        UserNotFound exception = assertThrows(
                UserNotFound.class,
                () -> createRestaurantUseCase.execute(request)
        );

        // Assert
        assertEquals(
                new UserNotFound(request.getUserId()).getMessage(),
                exception.getMessage()
        );

        verify(userGateway).existsById(request.getUserId());
        verify(restaurantGateway, never()).existsByName(any());
        verify(restaurantGateway, never()).save(any());

        verifyNoMoreInteractions(userGateway, restaurantGateway);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFound when restaurant name already exists")
    void shouldThrowRestaurantNotFoundWhenRestaurantNameAlreadyExists() {
        // Arrange
        when(userGateway.existsById(request.getUserId()))
                .thenReturn(true);

        when(restaurantGateway.existsByName(request.getName()))
                .thenReturn(true);

        // Act
        RestaurantNotFound exception = assertThrows(
                RestaurantNotFound.class,
                () -> createRestaurantUseCase.execute(request)
        );

        // Assert
        assertEquals(
                "The restaurant already exists.",
                exception.getMessage()
        );

        verify(userGateway).existsById(request.getUserId());
        verify(restaurantGateway).existsByName(request.getName());
        verify(restaurantGateway, never()).save(any());

        verifyNoMoreInteractions(userGateway, restaurantGateway);
    }
}
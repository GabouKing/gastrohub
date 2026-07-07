package com.example.gastrohub.application.restaurant.usecase;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
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

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    private Restaurant restaurant;
    private UpdateRestaurantInput request;

    private static final Long RESTAURANT_ID = 1L;

    @BeforeEach
    void setUp() throws Exception {

        restaurant = new Restaurant(
                "Pizza House",
                "123 Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                1L
        );

        setId(restaurant, RESTAURANT_ID);

        request = new UpdateRestaurantInput(
                1L,
                "Burger House",
                "456 Main Street",
                CuisineType.BRAZILIAN,
                "09:00-23:00",
                1L

        );
    }

    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurantSuccessfully() {

        when(restaurantGateway.findById(RESTAURANT_ID))
                .thenReturn(Optional.of(restaurant));

        when(restaurantGateway.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RestaurantOutput response =
                updateRestaurantUseCase.execute(RESTAURANT_ID, request);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(RESTAURANT_ID, response.getId()),
                () -> assertEquals(request.getName(), response.getName()),
                () -> assertEquals(request.getAddress(), response.getAddress()),
                () -> assertEquals(request.getCuisineType(), response.getCuisineType()),
                () -> assertEquals(request.getOpeningHours(), response.getOpeningHours()),
                () -> assertEquals(1L, response.getUserId())
        );

        verify(restaurantGateway).findById(RESTAURANT_ID);
        verify(restaurantGateway).save(restaurant);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    @DisplayName("Should throw RestaurantNotFound when restaurant does not exist")
    void shouldThrowRestaurantNotFoundWhenRestaurantDoesNotExist() {

        when(restaurantGateway.findById(RESTAURANT_ID))
                .thenReturn(Optional.empty());

        RestaurantNotFound exception = assertThrows(
                RestaurantNotFound.class,
                () -> updateRestaurantUseCase.execute(RESTAURANT_ID, request)
        );

        assertEquals("Restaurant not found.", exception.getMessage());

        verify(restaurantGateway).findById(RESTAURANT_ID);
        verify(restaurantGateway, never()).save(any());

        verifyNoMoreInteractions(restaurantGateway);
    }

    private void setId(Restaurant restaurant, Long id) throws Exception {
        Field field = Restaurant.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(restaurant, id);
    }
}
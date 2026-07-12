package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.CreateMenuItemInput;
import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateMenuItemUseCaseTest {
    private final MenuItemGateway menuItemGateway = mock(MenuItemGateway.class);
    private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);
    private final CreateMenuItemUseCase useCase = new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);

    @Test
    void shouldCreateMenuItemWhenRestaurantExists() {
        var input = new CreateMenuItemInput(
                1L,
                "Burger",
                "House burger",
                BigDecimal.valueOf(29.90),
                false,
                "/images/burger.png"
        );
        var savedMenuItem = new MenuItem(
                10L,
                1L,
                "Burger",
                "House burger",
                BigDecimal.valueOf(29.90),
                false,
                "/images/burger.png"
        );

        when(restaurantGateway.existsById(1L)).thenReturn(true);
        when(menuItemGateway.save(org.mockito.ArgumentMatchers.any(MenuItem.class))).thenReturn(savedMenuItem);

        var output = useCase.execute(input);

        assertEquals(10L, output.getId());
        assertEquals(1L, output.getRestaurantId());
        assertEquals("Burger", output.getName());
        verify(menuItemGateway).save(org.mockito.ArgumentMatchers.any(MenuItem.class));
    }

    @Test
    void shouldThrowWhenRestaurantDoesNotExist() {
        var input = new CreateMenuItemInput(
                99L,
                "Burger",
                "House burger",
                BigDecimal.valueOf(29.90),
                false,
                "/images/burger.png"
        );

        when(restaurantGateway.existsById(99L)).thenReturn(false);

        assertThrows(RestaurantNotFound.class, () -> useCase.execute(input));
        verify(menuItemGateway, never()).save(org.mockito.ArgumentMatchers.any(MenuItem.class));
    }
}

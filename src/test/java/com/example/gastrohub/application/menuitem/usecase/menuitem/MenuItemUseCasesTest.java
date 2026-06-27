package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.UpdateMenuItemInput;
import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuItemUseCasesTest {
    private final MenuItemGateway menuItemGateway = mock(MenuItemGateway.class);
    private final RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);

    @Test
    void shouldFindMenuItemById() {
        var menuItem = defaultMenuItem();
        when(menuItemGateway.findById(10L)).thenReturn(Optional.of(menuItem));

        var output = new FindMenuItemByIdUseCase(menuItemGateway).execute(10L);

        assertEquals(10L, output.getId());
        assertEquals("Burger", output.getName());
    }

    @Test
    void shouldThrowWhenMenuItemDoesNotExistOnFind() {
        when(menuItemGateway.findById(10L)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFound.class, () -> new FindMenuItemByIdUseCase(menuItemGateway).execute(10L));
    }

    @Test
    void shouldListMenuItemsByRestaurant() {
        when(restaurantGateway.existsById(1L)).thenReturn(true);
        when(menuItemGateway.findByRestaurantId(1L)).thenReturn(List.of(defaultMenuItem()));

        var output = new ListMenuItemsByRestaurantUseCase(menuItemGateway, restaurantGateway).execute(1L);

        assertEquals(1, output.size());
        assertEquals(1L, output.get(0).getRestaurantId());
    }

    @Test
    void shouldThrowWhenRestaurantDoesNotExistOnList() {
        when(restaurantGateway.existsById(99L)).thenReturn(false);

        assertThrows(
                RestaurantNotFound.class,
                () -> new ListMenuItemsByRestaurantUseCase(menuItemGateway, restaurantGateway).execute(99L)
        );
    }

    @Test
    void shouldUpdateMenuItem() {
        var menuItem = defaultMenuItem();
        var input = new UpdateMenuItemInput(
                10L,
                "Updated burger",
                "Updated description",
                BigDecimal.valueOf(35.90),
                true,
                "/images/updated-burger.png"
        );

        when(menuItemGateway.findById(10L)).thenReturn(Optional.of(menuItem));
        when(menuItemGateway.save(menuItem)).thenReturn(menuItem);

        var output = new UpdateMenuItemUseCase(menuItemGateway).execute(input);

        assertEquals("Updated burger", output.getName());
        assertEquals(BigDecimal.valueOf(35.90), output.getPrice());
        assertEquals(true, output.getAvailableOnlyOnRestaurant());
    }

    @Test
    void shouldThrowWhenMenuItemDoesNotExistOnUpdate() {
        var input = new UpdateMenuItemInput(
                10L,
                "Updated burger",
                "Updated description",
                BigDecimal.valueOf(35.90),
                true,
                "/images/updated-burger.png"
        );
        when(menuItemGateway.findById(10L)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFound.class, () -> new UpdateMenuItemUseCase(menuItemGateway).execute(input));
    }

    @Test
    void shouldDeleteMenuItem() {
        when(menuItemGateway.findById(10L)).thenReturn(Optional.of(defaultMenuItem()));

        new DeleteMenuItemUseCase(menuItemGateway).execute(10L);

        verify(menuItemGateway).deleteById(10L);
    }

    @Test
    void shouldThrowWhenMenuItemDoesNotExistOnDelete() {
        when(menuItemGateway.findById(10L)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFound.class, () -> new DeleteMenuItemUseCase(menuItemGateway).execute(10L));
    }

    private MenuItem defaultMenuItem() {
        return new MenuItem(
                10L,
                1L,
                "Burger",
                "House burger",
                BigDecimal.valueOf(29.90),
                false,
                "/images/burger.png"
        );
    }
}

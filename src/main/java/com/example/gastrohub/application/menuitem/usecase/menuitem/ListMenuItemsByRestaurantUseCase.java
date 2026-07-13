package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.mapper.MenuItemApplicationMapper;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMenuItemsByRestaurantUseCase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public ListMenuItemsByRestaurantUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public List<MenuItemOutput> execute(Long restaurantId) {
        if (!restaurantGateway.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId.toString());
        }

        return menuItemGateway.findByRestaurantId(restaurantId)
                .stream()
                .map(MenuItemApplicationMapper::toOutput)
                .toList();
    }
}

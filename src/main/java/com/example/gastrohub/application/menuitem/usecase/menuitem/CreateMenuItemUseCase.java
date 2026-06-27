package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.CreateMenuItemInput;
import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.mapper.MenuItemApplicationMapper;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import org.springframework.stereotype.Service;

@Service
public class CreateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItemOutput execute(CreateMenuItemInput input) {
        if (!restaurantGateway.existsById(input.getRestaurantId())) {
            throw new RestaurantNotFound(input.getRestaurantId());
        }

        var menuItem = MenuItemApplicationMapper.toDomain(input);
        var savedMenuItem = menuItemGateway.save(menuItem);

        return MenuItemApplicationMapper.toOutput(savedMenuItem);
    }
}

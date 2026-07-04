package com.example.gastrohub.application.menuitem.mapper;

import com.example.gastrohub.application.menuitem.dto.menuitem.CreateMenuItemInput;
import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.domain.menuitem.MenuItem;

public class MenuItemApplicationMapper {
    private MenuItemApplicationMapper() {
    }

    public static MenuItem toDomain(CreateMenuItemInput input) {
        return new MenuItem(
                null,
                input.getRestaurantId(),
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getAvailableOnlyOnRestaurant(),
                input.getPhotoPath()
        );
    }

    public static MenuItemOutput toOutput(MenuItem menuItem) {
        return new MenuItemOutput(
                menuItem.getId(),
                menuItem.getRestaurantId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyOnRestaurant(),
                menuItem.getPhotoPath()
        );
    }
}

package com.example.gastrohub.presentation.menuitem.mapper;

import com.example.gastrohub.application.menuitem.dto.menuitem.CreateMenuItemInput;
import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.dto.menuitem.UpdateMenuItemInput;
import com.example.gastrohub.presentation.menuitem.request.CreateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.request.UpdateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.response.MenuItemResponse;

public class MenuItemPresentationMapper {
    private MenuItemPresentationMapper() {
    }

    public static CreateMenuItemInput toInput(Long restaurantId, CreateMenuItemRequest request) {
        return new CreateMenuItemInput(
                restaurantId,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getAvailableOnlyOnRestaurant(),
                request.getPhotoPath()
        );
    }

    public static UpdateMenuItemInput toInput(Long id, UpdateMenuItemRequest request) {
        return new UpdateMenuItemInput(
                id,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getAvailableOnlyOnRestaurant(),
                request.getPhotoPath()
        );
    }

    public static MenuItemResponse toResponse(MenuItemOutput output) {
        return new MenuItemResponse(
                output.getId(),
                output.getRestaurantId(),
                output.getName(),
                output.getDescription(),
                output.getPrice(),
                output.getAvailableOnlyOnRestaurant(),
                output.getPhotoPath()
        );
    }
}

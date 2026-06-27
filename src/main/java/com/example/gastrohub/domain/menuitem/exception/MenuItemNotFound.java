package com.example.gastrohub.domain.menuitem.exception;

import lombok.Getter;

@Getter
public class MenuItemNotFound extends RuntimeException {
    private final Long menuItemId;

    public MenuItemNotFound(Long menuItemId) {
        super("Menu item not found: " + menuItemId);
        this.menuItemId = menuItemId;
    }
}

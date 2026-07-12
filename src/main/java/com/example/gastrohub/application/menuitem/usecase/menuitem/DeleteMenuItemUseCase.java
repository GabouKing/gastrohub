package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import org.springframework.stereotype.Service;

@Service
public class DeleteMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(Long id) {
        menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFound(id));

        menuItemGateway.deleteById(id);
    }
}

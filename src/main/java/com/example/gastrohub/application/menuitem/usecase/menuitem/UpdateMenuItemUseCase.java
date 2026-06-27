package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.dto.menuitem.UpdateMenuItemInput;
import com.example.gastrohub.application.menuitem.mapper.MenuItemApplicationMapper;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateMenuItemUseCase {
    private final MenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItemOutput execute(UpdateMenuItemInput input) {
        var menuItem = menuItemGateway.findById(input.getId())
                .orElseThrow(() -> new MenuItemNotFound(input.getId()));

        menuItem.update(
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getAvailableOnlyOnRestaurant(),
                input.getPhotoPath()
        );

        var updatedMenuItem = menuItemGateway.save(menuItem);

        return MenuItemApplicationMapper.toOutput(updatedMenuItem);
    }
}

package com.example.gastrohub.application.menuitem.usecase.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.mapper.MenuItemApplicationMapper;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import org.springframework.stereotype.Service;

@Service
public class FindMenuItemByIdUseCase {
    private final MenuItemGateway menuItemGateway;

    public FindMenuItemByIdUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItemOutput execute(Long id) {
        var menuItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFound(id));

        return MenuItemApplicationMapper.toOutput(menuItem);
    }
}

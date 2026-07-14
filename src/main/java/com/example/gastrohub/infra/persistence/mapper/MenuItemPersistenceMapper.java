package com.example.gastrohub.infra.persistence.mapper;

import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.infra.persistence.entity.MenuItemJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuItemPersistenceMapper {
    public MenuItemJpaEntity toEntity(MenuItem menuItem) {
        return new MenuItemJpaEntity(
                menuItem.getId(),
                menuItem.getRestaurantId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyOnRestaurant(),
                menuItem.getPhotoPath()
        );
    }

    public MenuItem toDomain(MenuItemJpaEntity entity) {
        return new MenuItem(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getAvailableOnlyOnRestaurant(),
                entity.getPhotoPath()
        );
    }
}

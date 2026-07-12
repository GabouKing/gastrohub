package com.example.gastrohub.domain.menuitem;

import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}

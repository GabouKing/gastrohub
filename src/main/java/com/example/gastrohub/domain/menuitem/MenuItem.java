package com.example.gastrohub.domain.menuitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MenuItem {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyOnRestaurant;
    private String photoPath;

    public void update(
            String name,
            String description,
            BigDecimal price,
            Boolean availableOnlyOnRestaurant,
            String photoPath
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyOnRestaurant = availableOnlyOnRestaurant;
        this.photoPath = photoPath;
    }
}

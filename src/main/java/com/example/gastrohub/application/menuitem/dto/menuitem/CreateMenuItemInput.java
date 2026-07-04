package com.example.gastrohub.application.menuitem.dto.menuitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateMenuItemInput {
    private Long restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyOnRestaurant;
    private String photoPath;
}

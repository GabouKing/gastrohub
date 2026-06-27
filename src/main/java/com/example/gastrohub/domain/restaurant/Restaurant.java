package com.example.gastrohub.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String cuisineType;
    private String openingHours;
    private Long ownerId;
}

package com.example.gastrohub.application.restaurant.dto;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateRestaurantInput {
    private String name;
    private String address;
    private CuisineType cuisineType;
    private String openingHours;
    private Long userId;
}

package com.example.gastrohub.presentation.restaurant.request;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(
        name = "CreateRestaurantRequest",
        description = "Representa os dados de cadastro de um restaurante."
)
public class CreateRestaurantRequest {
    private static final String OPENING_HOURS_REGEX =
            "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$";



    @Schema(
            description = "Nome do restaurante",
            example = "Pizzaria Napoli"
    )
    @NotBlank(message = "Restaurant name is required.")
    @Size(min = 3, max = 100,
            message = "Restaurant name must contain between 3 and 100 characters.")
    private String name;

    @Schema(
            description = "Endereço",
            example = "Rua das Flores, 100"
    )
    @NotBlank(message = "Address is required.")
    @Size(min = 10, max = 255,
            message = "Address must contain between 10 and 255 characters.")
    private String address;

    @Schema(
            description = "Tipo de culinária",
            example = "ITALIAN"
    )
    @NotNull(message = "Cuisine type is required.")
    private CuisineType cuisineType;

    @Schema(
            description = "Horário de funcionamento",
            example = "08:00-22:00"
    )
    @NotBlank(message = "Opening hours are required.")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "Opening hours must follow the format HH:mm-HH:mm."
    )
    private String openingHours;

    @Schema(
            description = "ID do proprietário",
            example = "1"
    )
    @NotNull(message = "User is required.")
    private Long userId;
}

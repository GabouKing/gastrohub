package com.example.gastrohub.presentation.restaurant.request;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(
        name = "UpdateRestaurantRequest",
        description = "Representa os dados de atualizção de um restaurante."
)
public class UpdateRestaurantRequest {
    private static final String OPENING_HOURS_REGEX =
            "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$";

    @Schema(
            description = "Nome do restaurante.",
            example = "Pizzaria Bella Napoli",
            minLength = 3,
            maxLength = 100
    )
    @NotBlank(message = "Restaurant name is required.")
    @Size(
            min = 3,
            max = 100,
            message = "Restaurant name must contain between 3 and 100 characters."
    )
    private String name;

    @Schema(
            description = "Endereço do restaurante.",
            example = "Rua das Flores, 150 - Centro"
    )
    @NotBlank(message = "Restaurant address is required.")
    @Size(
            min = 5,
            max = 255,
            message = "Restaurant address must contain between 5 and 255 characters."
    )
    private String address;

    @Schema(
            description = "Tipo de culinária.",
            example = "ITALIAN"
    )
    @NotNull(message = "Cuisine type is required.")
    private CuisineType cuisineType;

    @Schema(
            description = "Horário de funcionamento no formato HH:mm-HH:mm.",
            example = "18:00-23:00",
            pattern = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$"
    )
    @NotBlank(message = "Opening hours are required.")
    @Pattern(
            regexp = OPENING_HOURS_REGEX,
            message = "Opening hours must follow the format HH:mm-HH:mm."
    )
    private String openingHours;

    @Schema(
            description = "Identificador do usuário proprietário do restaurante.",
            example = "1"
    )
    @NotNull(message = "User id is required.")
    @Positive(message = "User id must be greater than zero.")
    private Long userId;

}


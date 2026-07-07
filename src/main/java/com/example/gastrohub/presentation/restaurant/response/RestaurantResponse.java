package com.example.gastrohub.presentation.restaurant.response;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(
        name = "RestaurantResponse",
        description = "Representa os dados de um restaurante."
)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RestaurantResponse {

    @Schema(
            description = "Identificador único do restaurante.",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nome do restaurante.",
            example = "Pizzaria Bella Napoli"
    )
    private String name;

    @Schema(
            description = "Endereço do restaurante.",
            example = "Rua das Flores, 150 - Centro"
    )
    private String address;

    @Schema(
            description = "Tipo de culinária do restaurante.",
            example = "ITALIAN",
            allowableValues = {
                    "BRAZILIAN",
                    "ITALIAN",
                    "JAPANESE",
                    "CHINESE",
                    "MEXICAN",
                    "FAST_FOOD"
            }
    )
    private CuisineType cuisineType;

    @Schema(
            description = "Horário de funcionamento no formato HH:mm-HH:mm.",
            example = "18:00-23:00"
    )
    private String openingHours;

    @Schema(
            description = "Identificador do usuário proprietário do restaurante.",
            example = "1"
    )
    private Long userId;
}
package com.example.gastrohub.presentation.menuitem.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "CreateMenuItemRequest",
        description = "Objeto utilizado para cadastrar um novo item do cardápio."
)
public class CreateMenuItemRequest {

    @Schema(
            description = "Nome do item do cardápio.",
            example = "Hambúrguer Artesanal"
    )
    @NotBlank(message = "O nome do item é obrigatório.")
    private String name;

    @Schema(
            description = "Descrição detalhada do item.",
            example = "Pão brioche, hambúrguer bovino 180g, queijo cheddar, alface e molho especial."
    )
    private String description;

    @Schema(
            description = "Preço do item.",
            example = "39.90",
            minimum = "0.01"
    )
    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    private BigDecimal price;

    @Schema(
            description = "Indica se o item está disponível apenas para consumo no restaurante.",
            example = "false"
    )
    @NotNull(message = "A disponibilidade é obrigatória.")
    private Boolean availableOnlyOnRestaurant;

    @Schema(
            description = "Caminho ou URL da foto do item.",
            example = "https://gastrohub.com/images/menu/hamburguer-artesanal.jpg",
            nullable = true
    )
    private String photoPath;
}

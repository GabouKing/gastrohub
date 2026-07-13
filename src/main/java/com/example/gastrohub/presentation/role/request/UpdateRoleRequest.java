
package com.example.gastrohub.presentation.role.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UpdateRoleRequest",
        description = "Objeto utilizado para atualizar as informações de um perfil de acesso."
)
public class UpdateRoleRequest {

    @Schema(
            description = "Nome único do perfil de acesso.",
            example = "ROLE_MANAGER",
            maxLength = 50
    )
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @Schema(
            description = "Descrição do perfil de acesso e suas responsabilidades.",
            example = "Perfil responsável pelo gerenciamento operacional do sistema.",
            maxLength = 255,
            nullable = true
    )
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;
}


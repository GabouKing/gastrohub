package com.example.gastrohub.presentation.user.response;

import com.example.gastrohub.presentation.restaurant.response.RestaurantResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Schema(
        name = "UserResponse",
        description = "Representa os dados de um usuário."
)
public class UserResponse {

    @Schema(
            description = "Identificador único do usuário.",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nome completo do usuário.",
            example = "João Marcos"
    )
    private String name;

    @Schema(
            description = "Endereço de e-mail do usuário.",
            example = "joao.marcos@email.com"
    )
    private String email;

    @Schema(
            description = "Login utilizado para autenticação.",
            example = "joaomarcos"
    )
    private String login;

    @Schema(
            description = """
                    Código do perfil de acesso do usuário.

                    Exemplos:
                    0 - ADMIN
                    1 - USER_OWNER
                    2 - CUSTOMER
                    """,
            example = "1"
    )
    private Integer role;

    @ArraySchema(
            schema = @Schema(implementation = RestaurantResponse.class),
            arraySchema = @Schema(
                    description = "Lista de restaurantes associados ao usuário."
            )
    )
    private List<RestaurantResponse> restaurants;
}
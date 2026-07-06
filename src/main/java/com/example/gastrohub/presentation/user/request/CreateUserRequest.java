package com.example.gastrohub.presentation.user.request;

import com.example.gastrohub.domain.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @Schema(
            description = "Nome completo do usuário.",
            example = "João Marcos"
    )
    @NotBlank(message = "Name is required.")
    @Size(
            min = 3,
            max = 100,
            message = "Name must contain between 3 and 100 characters."
    )
    private String name;

    @Schema(
            description = "Endereço de e-mail do usuário.",
            example = "joao.marcos@email.com"
    )
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    @Size(
            max = 255,
            message = "Email must contain at most 255 characters."
    )
    private String email;

    @Schema(
            description = "Login utilizado para autenticação.",
            example = "joaomarcos"
    )
    @NotBlank(message = "Login is required.")
    @Size(
            min = 3,
            max = 50,
            message = "Login must contain between 3 and 50 characters."
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "Login may contain only letters, numbers, dots, underscores and hyphens."
    )
    private String login;

    @Schema(
            description = "Senha do usuário. Deve conter no mínimo 8 caracteres, incluindo letra maiúscula, letra minúscula, número e caractere especial.",
            example = "Senha@123"
    )
    @NotBlank(message = "Password is required.")
    @Size(
            min = 8,
            max = 100,
            message = "Password must contain between 8 and 100 characters."
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.#_-]).{8,100}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number and one special character."
    )
    private String password;

    @Schema(
            description = "Perfil de acesso do usuário.",
            example = "USER_OWNER"
    )
    @NotNull(message = "User role is required.")
    private UserRole role;
}

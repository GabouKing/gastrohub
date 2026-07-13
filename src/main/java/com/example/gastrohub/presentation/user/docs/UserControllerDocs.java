package com.example.gastrohub.presentation.user.docs;

import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Users",
        description = "Operações relacionadas ao gerenciamento de usuários."
)
public interface UserControllerDocs {

    @Operation(
            summary = "Criar usuário",
            description = """
                    Cadastra um novo usuário no sistema.
                    
                    Regras de negócio:
                    - O nome é obrigatório.
                    - O e-mail deve ser válido e único.
                    - O login deve ser único.
                    - A senha deve atender aos requisitos de segurança.
                    - O perfil (role) deve ser um valor válido.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail ou login já cadastrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<UserResponse> createUser(
            CreateUserRequest createUserRequest
    );

    // ----------------------------------------------------------------------

    @Operation(
            summary = "Atualizar usuário",
            description = """
                    Atualiza os dados de um usuário existente.
                    
                    O usuário deve existir para que a atualização seja realizada.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "E-mail ou login já cadastrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<UserResponse> updateUser(

            @Parameter(
                    description = "Identificador do usuário.",
                    example = "1"
            )
            Long id,

            UpdateUserRequest updateUserRequest
    );

    // ----------------------------------------------------------------------

    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<UserResponse>> allUsers();

    // ----------------------------------------------------------------------

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<UserResponse> user(

            @Parameter(
                    description = "Identificador do usuário.",
                    example = "1"
            )
            Long id
    );

    // ----------------------------------------------------------------------

    @Operation(
            summary = "Excluir usuário",
            description = """
                    Remove um usuário do sistema.
                    
                    A exclusão é permanente e não poderá ser desfeita.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> deleteUser(

            @Parameter(
                    description = "Identificador do usuário.",
                    example = "1"
            )
            Long id
    );
}


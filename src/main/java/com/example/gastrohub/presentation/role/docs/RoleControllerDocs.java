package com.example.gastrohub.presentation.role.docs;

import com.example.gastrohub.presentation.role.request.CreateRoleRequest;
import com.example.gastrohub.presentation.role.request.UpdateRoleRequest;
import com.example.gastrohub.presentation.role.response.RoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Perfis",
        description = "Endpoints responsáveis pelo gerenciamento dos perfis (roles) utilizados para controle de acesso da aplicação."
)
public interface RoleControllerDocs {

    @Operation(
            summary = "Cadastrar perfil",
            description = "Cria um novo perfil de acesso no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Perfil cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe um perfil com o nome informado",
                    content = @Content
            )
    })
    ResponseEntity<RoleResponse> createRole(
            @Valid
            @Parameter(description = "Dados para criação do perfil")
            CreateRoleRequest request
    );

    @Operation(
            summary = "Listar perfis",
            description = "Retorna todos os perfis cadastrados no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de perfis retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponse.class))
            )
    })
    ResponseEntity<List<RoleResponse>> listRoles();

    @Operation(
            summary = "Buscar perfil por ID",
            description = "Retorna os detalhes de um perfil a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<RoleResponse> findRoleById(

            @Parameter(
                    description = "Identificador do perfil",
                    example = "1",
                    required = true
            )
            Long id
    );

    @Operation(
            summary = "Atualizar perfil",
            description = "Atualiza as informações de um perfil existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe outro perfil com o nome informado",
                    content = @Content
            )
    })
    ResponseEntity<RoleResponse> updateRole(

            @Parameter(
                    description = "Identificador do perfil",
                    example = "1",
                    required = true
            )
            Long id,

            @Valid
            @Parameter(description = "Novos dados do perfil")
            UpdateRoleRequest request
    );

    @Operation(
            summary = "Excluir perfil",
            description = "Remove um perfil do sistema pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Perfil removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil não encontrado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "O perfil não pode ser removido porque está associado a usuários",
                    content = @Content
            )
    })
    ResponseEntity<Void> deleteRole(

            @Parameter(
                    description = "Identificador do perfil",
                    example = "1",
                    required = true
            )
            Long id
    );
}


package com.example.gastrohub.presentation.menuitem.docs;

import com.example.gastrohub.presentation.menuitem.request.CreateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.request.UpdateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.response.MenuItemResponse;
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
        name = "Itens do Cardápio",
        description = "Endpoints responsáveis pelo gerenciamento dos itens de cardápio de um restaurante."
)
public interface MenuItemControllerDocs {

    @Operation(
            summary = "Cadastrar item do cardápio",
            description = "Cria um novo item de cardápio associado ao restaurante informado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Item do cardápio cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = MenuItemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<MenuItemResponse> createMenuItem(
            @Parameter(
                    description = "Identificador do restaurante",
                    example = "1",
                    required = true
            )
            Long restaurantId,

            @Valid
            @Parameter(description = "Dados do item do cardápio")
            CreateMenuItemRequest request
    );

    @Operation(
            summary = "Listar itens do cardápio",
            description = "Retorna todos os itens cadastrados para um restaurante."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de itens retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = MenuItemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<List<MenuItemResponse>> listMenuItemsByRestaurant(

            @Parameter(
                    description = "Identificador do restaurante",
                    example = "1",
                    required = true
            )
            Long restaurantId
    );

    @Operation(
            summary = "Buscar item do cardápio por ID",
            description = "Retorna os detalhes de um item do cardápio a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = MenuItemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item do cardápio não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<MenuItemResponse> findMenuItemById(

            @Parameter(
                    description = "Identificador do item do cardápio",
                    example = "10",
                    required = true
            )
            Long id
    );

    @Operation(
            summary = "Atualizar item do cardápio",
            description = "Atualiza as informações de um item do cardápio existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Item atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MenuItemResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item do cardápio não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<MenuItemResponse> updateMenuItem(

            @Parameter(
                    description = "Identificador do item do cardápio",
                    example = "10",
                    required = true
            )
            Long id,

            @Valid
            @Parameter(description = "Novos dados do item do cardápio")
            UpdateMenuItemRequest request
    );

    @Operation(
            summary = "Excluir item do cardápio",
            description = "Remove um item do cardápio pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Item removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item do cardápio não encontrado",
                    content = @Content
            )
    })
    ResponseEntity<Void> deleteMenuItem(

            @Parameter(
                    description = "Identificador do item do cardápio",
                    example = "10",
                    required = true
            )
            Long id
    );
}


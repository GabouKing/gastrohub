package com.example.gastrohub.presentation.restaurant.docs;

import com.example.gastrohub.presentation.restaurant.request.CreateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.request.UpdateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.response.RestaurantResponse;
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

@Tag(name = "Restaurants", description = "Operações relacionadas aos restaurantes")
public interface RestaurantControllerDocs {

    @Operation(
            summary = "Criar restaurante",
            description = """
                    Cria um novo restaurante no sistema.
                    
                    Regras de negócio:
                    - O nome deve possuir entre 3 e 100 caracteres.
                    - O endereço é obrigatório.
                    - O tipo de culinária deve ser um valor válido do enum CuisineType.
                    - O horário de funcionamento deve estar no formato HH:mm-HH:mm.
                    - O usuário proprietário deve existir.
                    - Não pode existir outro restaurante com o mesmo nome.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso",
                    content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário proprietário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe um restaurante com este nome"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<RestaurantResponse> createRestaurant(CreateRestaurantRequest request);

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Retorna um restaurante a partir do seu identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                    content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<RestaurantResponse> findRestaurantById(
            @Parameter(description = "ID do restaurante", example = "1")
            Long id);

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Buscar restaurante por nome",
            description = "Retorna um restaurante utilizando seu nome exato."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                    content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<RestaurantResponse> findRestaurantByName(
            @Parameter(description = "Nome do restaurante", example = "Pizzaria Bella Napoli")
            String name);

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Listar restaurantes",
            description = "Retorna todos os restaurantes cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de restaurantes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<RestaurantResponse>> findAll();

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Pesquisar restaurantes",
            description = "Realiza uma busca parcial pelo nome do restaurante."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de restaurantes encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class)))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<List<RestaurantResponse>> findRestaurantByNameContaining(
            @Parameter(
                    description = "Trecho do nome do restaurante",
                    example = "Pizza"
            )
            String name);

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Atualizar restaurante",
            description = """
                    Atualiza os dados de um restaurante existente.
                    
                    Apenas os dados enviados na requisição serão atualizados,
                    respeitando todas as regras de validação da entidade.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe outro restaurante com este nome"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<RestaurantResponse> updateRestaurant(
            @Parameter(description = "ID do restaurante", example = "1")
            Long id,

            UpdateRestaurantRequest request);

    // ------------------------------------------------------------------------

    @Operation(
            summary = "Excluir restaurante",
            description = """
                    Remove um restaurante do sistema.
                    
                    A exclusão é permanente e não pode ser desfeita.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> deleteRestaurant(
            @Parameter(description = "ID do restaurante", example = "1")
            Long id);
}
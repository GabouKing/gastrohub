package com.example.gastrohub.presentation.restaurant;

import com.example.gastrohub.application.restaurant.dto.CreateRestaurantInput;
import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.dto.UpdateRestaurantInput;
import com.example.gastrohub.application.restaurant.usecase.CreateRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.DeleteRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByIdUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameContaining;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameUseCase;
import com.example.gastrohub.application.restaurant.usecase.ListRestaurantsUseCase;
import com.example.gastrohub.application.restaurant.usecase.UpdateRestaurantUseCase;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.presentation.restaurant.request.CreateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.request.UpdateRestaurantRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    private static final String BASE_URL = "/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateRestaurantUseCase createRestaurantUseCase;

    @MockitoBean
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @MockitoBean
    private FindRestaurantByNameUseCase findRestaurantByNameUseCase;

    @MockitoBean
    private ListRestaurantsUseCase listRestaurantsUseCase;

    @MockitoBean
    private FindRestaurantByNameContaining findRestaurantByNameContaining;

    @MockitoBean
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @MockitoBean
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Test
    @DisplayName("Should create restaurant successfully")
    void shouldCreateRestaurantSuccessfully() throws Exception {

        var request = createRequest();

        given(createRestaurantUseCase.execute(any(CreateRestaurantInput.class)))
                .willReturn(output());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger House"))
                .andExpect(jsonPath("$.address").value("Main Street"))
                .andExpect(jsonPath("$.cuisineType").value("ITALIAN"))
                .andExpect(jsonPath("$.openingHours").value("08:00-22:00"))
                .andExpect(jsonPath("$.userId").value(10));
    }

    @Test
    @DisplayName("Should find restaurant by id")
    void shouldFindRestaurantById() throws Exception {

        given(findRestaurantByIdUseCase.execute(1L))
                .willReturn(output());

        mockMvc.perform(get(BASE_URL + "/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger House"));
    }

    @Test
    @DisplayName("Should find restaurant by name")
    void shouldFindRestaurantByName() throws Exception {

        given(findRestaurantByNameUseCase.execute("Burger House"))
                .willReturn(output());

        mockMvc.perform(get(BASE_URL + "/name/{name}", "Burger House"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burger House"));
    }

    @Test
    @DisplayName("Should list all restaurants")
    void shouldListRestaurants() throws Exception {

        given(listRestaurantsUseCase.execute())
                .willReturn(List.of(output()));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Burger House"));
    }

    @Test
    @DisplayName("Should search restaurants by name")
    void shouldSearchRestaurantsByName() throws Exception {

        given(findRestaurantByNameContaining.execute("Burger"))
                .willReturn(List.of(output()));

        mockMvc.perform(get(BASE_URL + "/search")
                        .param("name", "Burger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Burger House"));
    }

    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurantSuccessfully() throws Exception {

        var request = updateRequest();

        given(updateRestaurantUseCase.execute(eq(1L), any(UpdateRestaurantInput.class)))
                .willReturn(output());

        mockMvc.perform(put(BASE_URL + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burger House"));
    }

    @Test
    @DisplayName("Should delete restaurant successfully")
    void shouldDeleteRestaurantSuccessfully() throws Exception {

        doNothing().when(deleteRestaurantUseCase).execute(1L);

        mockMvc.perform(delete(BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk());
    }

    private CreateRestaurantRequest createRequest() {
        return new CreateRestaurantRequest(
                "Burger House",
                "Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",
                10L
        );
    }

    private UpdateRestaurantRequest updateRequest() {
        return new UpdateRestaurantRequest(
                "Burger House",
                "Main Street",
                CuisineType.ITALIAN,
                "08:00-22:00",

                10L
        );
    }

    private RestaurantOutput output() {
        return RestaurantOutput.builder()
                .id(1L)
                .name("Burger House")
                .address("Main Street")
                .cuisineType(CuisineType.ITALIAN)
                .openingHours("08:00-22:00")
                .userId(10L)
                .build();
    }
}
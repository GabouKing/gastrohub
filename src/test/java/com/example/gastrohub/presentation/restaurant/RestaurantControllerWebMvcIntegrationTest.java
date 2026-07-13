package com.example.gastrohub.presentation.restaurant;

import com.example.gastrohub.application.restaurant.dto.RestaurantOutput;
import com.example.gastrohub.application.restaurant.usecase.CreateRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.DeleteRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByIdUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameContaining;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameUseCase;
import com.example.gastrohub.application.restaurant.usecase.ListRestaurantsUseCase;
import com.example.gastrohub.application.restaurant.usecase.UpdateRestaurantUseCase;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerWebMvcIntegrationTest {

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
    @DisplayName("Should create restaurant through MockMvc")
    void shouldCreateRestaurant() throws Exception {
        given(createRestaurantUseCase.execute(any())).willReturn(restaurantOutput());

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRestaurantJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger House"))
                .andExpect(jsonPath("$.address").value("Main Street, 100"))
                .andExpect(jsonPath("$.cuisineType").value("ITALIAN"))
                .andExpect(jsonPath("$.openingHours").value("08:00-22:00"))
                .andExpect(jsonPath("$.userId").value(10));
    }

    @Test
    @DisplayName("Should find restaurant by id through MockMvc")
    void shouldFindRestaurantById() throws Exception {
        given(findRestaurantByIdUseCase.execute(1L)).willReturn(restaurantOutput());

        mockMvc.perform(get("/restaurants/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger House"));
    }

    @Test
    @DisplayName("Should find restaurant by name through MockMvc")
    void shouldFindRestaurantByName() throws Exception {
        given(findRestaurantByNameUseCase.execute("Burger House")).willReturn(restaurantOutput());

        mockMvc.perform(get("/restaurants/name/{name}", "Burger House"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger House"));
    }

    @Test
    @DisplayName("Should list restaurants through MockMvc")
    void shouldListRestaurants() throws Exception {
        given(listRestaurantsUseCase.execute()).willReturn(List.of(restaurantOutput()));

        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].cuisineType").value("ITALIAN"));
    }

    @Test
    @DisplayName("Should search restaurants through MockMvc")
    void shouldSearchRestaurants() throws Exception {
        given(findRestaurantByNameContaining.execute("Burger")).willReturn(List.of(restaurantOutput()));

        mockMvc.perform(get("/restaurants/search").param("name", "Burger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Burger House"));
    }

    @Test
    @DisplayName("Should update restaurant through MockMvc")
    void shouldUpdateRestaurant() throws Exception {
        given(updateRestaurantUseCase.execute(eq(1L), any())).willReturn(restaurantOutput());

        mockMvc.perform(put("/restaurants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRestaurantJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(10));
    }

    @Test
    @DisplayName("Should delete restaurant through MockMvc")
    void shouldDeleteRestaurant() throws Exception {
        doNothing().when(deleteRestaurantUseCase).execute(1L);

        mockMvc.perform(delete("/restaurants/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("Content-Type"))
                .andExpect(content().string(""));
    }

    private RestaurantOutput restaurantOutput() {
        return RestaurantOutput.builder()
                .id(1L)
                .name("Burger House")
                .address("Main Street, 100")
                .cuisineType(CuisineType.ITALIAN)
                .openingHours("08:00-22:00")
                .userId(10L)
                .build();
    }

    private String validRestaurantJson() throws Exception {
        return json(Map.of(
                "name", "Burger House",
                "address", "Main Street, 100",
                "cuisineType", "ITALIAN",
                "openingHours", "08:00-22:00",
                "userId", 10
        ));
    }

    private String json(Map<String, Object> body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }
}

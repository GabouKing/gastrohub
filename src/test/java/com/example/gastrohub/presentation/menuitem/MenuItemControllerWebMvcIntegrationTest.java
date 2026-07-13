package com.example.gastrohub.presentation.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.usecase.menuitem.CreateMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.DeleteMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.FindMenuItemByIdUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.ListMenuItemsByRestaurantUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.UpdateMenuItemUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
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

@WebMvcTest(MenuItemController.class)
class MenuItemControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateMenuItemUseCase createMenuItemUseCase;

    @MockitoBean
    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;

    @MockitoBean
    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;

    @MockitoBean
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @MockitoBean
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Test
    @DisplayName("Should create menu item through MockMvc")
    void shouldCreateMenuItem() throws Exception {
        given(createMenuItemUseCase.execute(any())).willReturn(menuItemOutput());

        mockMvc.perform(post("/restaurants/{restaurantId}/menu-items", 10L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMenuItemJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.restaurantId").value(10))
                .andExpect(jsonPath("$.name").value("Burger"))
                .andExpect(jsonPath("$.description").value("Burger artesanal"))
                .andExpect(jsonPath("$.price").value(39.90))
                .andExpect(jsonPath("$.availableOnlyOnRestaurant").value(true))
                .andExpect(jsonPath("$.photoPath").value("/images/burger.png"));
    }

    @Test
    @DisplayName("Should list menu items by restaurant through MockMvc")
    void shouldListMenuItemsByRestaurant() throws Exception {
        given(listMenuItemsByRestaurantUseCase.execute(10L)).willReturn(List.of(menuItemOutput()));

        mockMvc.perform(get("/restaurants/{restaurantId}/menu-items", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].restaurantId").value(10));
    }

    @Test
    @DisplayName("Should find menu item by id through MockMvc")
    void shouldFindMenuItemById() throws Exception {
        given(findMenuItemByIdUseCase.execute(1L)).willReturn(menuItemOutput());

        mockMvc.perform(get("/menu-items/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Burger"));
    }

    @Test
    @DisplayName("Should update menu item through MockMvc")
    void shouldUpdateMenuItem() throws Exception {
        given(updateMenuItemUseCase.execute(any())).willReturn(menuItemOutput());

        mockMvc.perform(put("/menu-items/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMenuItemJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(39.90));
    }

    @Test
    @DisplayName("Should delete menu item through MockMvc")
    void shouldDeleteMenuItem() throws Exception {
        doNothing().when(deleteMenuItemUseCase).execute(1L);

        mockMvc.perform(delete("/menu-items/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(header().doesNotExist("Content-Type"))
                .andExpect(content().string(""));
    }

    private MenuItemOutput menuItemOutput() {
        return new MenuItemOutput(
                1L,
                10L,
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );
    }

    private String validMenuItemJson() throws Exception {
        return json(Map.of(
                "name", "Burger",
                "description", "Burger artesanal",
                "price", 39.90,
                "availableOnlyOnRestaurant", true,
                "photoPath", "/images/burger.png"
        ));
    }

    private String json(Map<String, Object> body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }
}

package com.example.gastrohub.presentation.menuitem;

import com.example.gastrohub.application.menuitem.dto.menuitem.CreateMenuItemInput;
import com.example.gastrohub.application.menuitem.dto.menuitem.MenuItemOutput;
import com.example.gastrohub.application.menuitem.dto.menuitem.UpdateMenuItemInput;
import com.example.gastrohub.application.menuitem.usecase.menuitem.CreateMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.DeleteMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.FindMenuItemByIdUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.ListMenuItemsByRestaurantUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.UpdateMenuItemUseCase;
import com.example.gastrohub.presentation.menuitem.request.CreateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.request.UpdateMenuItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuItemControllerTest {

    private CreateMenuItemUseCase createMenuItemUseCase;
    private ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    private UpdateMenuItemUseCase updateMenuItemUseCase;
    private DeleteMenuItemUseCase deleteMenuItemUseCase;
    private MenuItemController controller;

    @BeforeEach
    void setUp() {
        createMenuItemUseCase = mock(CreateMenuItemUseCase.class);
        listMenuItemsByRestaurantUseCase = mock(ListMenuItemsByRestaurantUseCase.class);
        findMenuItemByIdUseCase = mock(FindMenuItemByIdUseCase.class);
        updateMenuItemUseCase = mock(UpdateMenuItemUseCase.class);
        deleteMenuItemUseCase = mock(DeleteMenuItemUseCase.class);
        controller = new MenuItemController(
                createMenuItemUseCase,
                listMenuItemsByRestaurantUseCase,
                findMenuItemByIdUseCase,
                updateMenuItemUseCase,
                deleteMenuItemUseCase
        );
    }

    @Test
    void shouldCreateMenuItem() {
        var request = new CreateMenuItemRequest(
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );
        when(createMenuItemUseCase.execute(org.mockito.ArgumentMatchers.any(CreateMenuItemInput.class)))
                .thenReturn(output());

        var response = controller.createMenuItem(10L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRestaurantId()).isEqualTo(10L);
        assertThat(response.getBody().getName()).isEqualTo("Burger");

        var inputCaptor = ArgumentCaptor.forClass(CreateMenuItemInput.class);
        verify(createMenuItemUseCase).execute(inputCaptor.capture());
        assertThat(inputCaptor.getValue().getRestaurantId()).isEqualTo(10L);
        assertThat(inputCaptor.getValue().getPrice()).isEqualByComparingTo("39.90");
    }

    @Test
    void shouldListMenuItemsByRestaurant() {
        when(listMenuItemsByRestaurantUseCase.execute(10L)).thenReturn(List.of(output()));

        var response = controller.listMenuItemsByRestaurant(10L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().getName()).isEqualTo("Burger");
        verify(listMenuItemsByRestaurantUseCase).execute(10L);
    }

    @Test
    void shouldFindMenuItemById() {
        when(findMenuItemByIdUseCase.execute(1L)).thenReturn(output());

        var response = controller.findMenuItemById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(findMenuItemByIdUseCase).execute(1L);
    }

    @Test
    void shouldUpdateMenuItem() {
        var request = new UpdateMenuItemRequest(
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );
        when(updateMenuItemUseCase.execute(org.mockito.ArgumentMatchers.any(UpdateMenuItemInput.class)))
                .thenReturn(output());

        var response = controller.updateMenuItem(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPhotoPath()).isEqualTo("/images/burger.png");

        var inputCaptor = ArgumentCaptor.forClass(UpdateMenuItemInput.class);
        verify(updateMenuItemUseCase).execute(inputCaptor.capture());
        assertThat(inputCaptor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void shouldDeleteMenuItem() {
        var response = controller.deleteMenuItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(deleteMenuItemUseCase).execute(1L);
    }

    private MenuItemOutput output() {
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
}

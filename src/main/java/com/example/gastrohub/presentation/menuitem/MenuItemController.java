package com.example.gastrohub.presentation.menuitem;

import com.example.gastrohub.application.menuitem.usecase.menuitem.CreateMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.DeleteMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.FindMenuItemByIdUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.ListMenuItemsByRestaurantUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.UpdateMenuItemUseCase;
import com.example.gastrohub.presentation.menuitem.mapper.MenuItemPresentationMapper;
import com.example.gastrohub.presentation.menuitem.request.CreateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.request.UpdateMenuItemRequest;
import com.example.gastrohub.presentation.menuitem.response.MenuItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuItemController {
    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    @PostMapping("/restaurants/{restaurantId}/menu-items")
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateMenuItemRequest request
    ) {
        var input = MenuItemPresentationMapper.toInput(restaurantId, request);
        var output = createMenuItemUseCase.execute(input);
        var response = MenuItemPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/restaurants/{restaurantId}/menu-items")
    public ResponseEntity<List<MenuItemResponse>> listMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        var response = listMenuItemsByRestaurantUseCase.execute(restaurantId)
                .stream()
                .map(MenuItemPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponse> findMenuItemById(@PathVariable Long id) {
        var output = findMenuItemByIdUseCase.execute(id);
        var response = MenuItemPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuItemRequest request
    ) {
        var input = MenuItemPresentationMapper.toInput(id, request);
        var output = updateMenuItemUseCase.execute(input);
        var response = MenuItemPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/menu-items/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

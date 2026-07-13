package com.example.gastrohub.presentation.exception;

import com.example.gastrohub.application.menuitem.usecase.menuitem.CreateMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.DeleteMenuItemUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.FindMenuItemByIdUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.ListMenuItemsByRestaurantUseCase;
import com.example.gastrohub.application.menuitem.usecase.menuitem.UpdateMenuItemUseCase;
import com.example.gastrohub.application.restaurant.usecase.CreateRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.DeleteRestaurantUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByIdUseCase;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameContaining;
import com.example.gastrohub.application.restaurant.usecase.FindRestaurantByNameUseCase;
import com.example.gastrohub.application.restaurant.usecase.ListRestaurantsUseCase;
import com.example.gastrohub.application.restaurant.usecase.UpdateRestaurantUseCase;
import com.example.gastrohub.application.role.usecase.CreateRoleUseCase;
import com.example.gastrohub.application.role.usecase.DeleteRoleUseCase;
import com.example.gastrohub.application.role.usecase.FindRoleByIdUseCase;
import com.example.gastrohub.application.role.usecase.ListRoleUseCase;
import com.example.gastrohub.application.role.usecase.UpdateRoleUseCase;
import com.example.gastrohub.application.user.usecase.user.CreateUserUseCase;
import com.example.gastrohub.application.user.usecase.user.DeleteUserUseCase;
import com.example.gastrohub.application.user.usecase.user.FindUserByIdUseCase;
import com.example.gastrohub.application.user.usecase.user.ListUserUseCase;
import com.example.gastrohub.application.user.usecase.user.UpdateUserUseCase;
import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import com.example.gastrohub.domain.restaurant.exception.InvalidCuisineTypeException;
import com.example.gastrohub.domain.restaurant.exception.InvalidOpeningHoursException;
import com.example.gastrohub.domain.restaurant.exception.InvalidRestaurantAddressException;
import com.example.gastrohub.domain.restaurant.exception.InvalidRestaurantNameException;
import com.example.gastrohub.domain.restaurant.exception.InvalidUserException;
import com.example.gastrohub.domain.restaurant.exception.RestaurantAlreadyExistsException;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import com.example.gastrohub.presentation.menuitem.MenuItemController;
import com.example.gastrohub.presentation.restaurant.RestaurantController;
import com.example.gastrohub.presentation.role.RoleController;
import com.example.gastrohub.presentation.user.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        UserController.class,
        RoleController.class,
        RestaurantController.class,
        MenuItemController.class
})
class GlobalExceptionHandlerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private ListUserUseCase listUserUseCase;

    @MockitoBean
    private FindUserByIdUseCase findUserByIdUseCase;

    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockitoBean
    private CreateRoleUseCase createRoleUseCase;

    @MockitoBean
    private FindRoleByIdUseCase findRoleByIdUseCase;

    @MockitoBean
    private ListRoleUseCase listRoleUseCase;

    @MockitoBean
    private UpdateRoleUseCase updateRoleUseCase;

    @MockitoBean
    private DeleteRoleUseCase deleteRoleUseCase;

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
    @DisplayName("Should map UserNotFound to problem detail")
    void shouldMapUserNotFound() throws Exception {
        given(findUserByIdUseCase.execute(99L)).willThrow(new UserNotFound(99L));

        mockMvc.perform(get("/users/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("/problems/user-not-found"))
                .andExpect(jsonPath("$.title").value("User not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("The requested user was not found."))
                .andExpect(jsonPath("$.instance").value("/users/99"))
                .andExpect(jsonPath("$.invalid_value").value(99));
    }

    @Test
    @DisplayName("Should map EmailAlreadyExistsException to problem detail")
    void shouldMapEmailAlreadyExists() throws Exception {
        given(createUserUseCase.execute(any()))
                .willThrow(new EmailAlreadyExistsException("owner@gastrohub.com"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "name", "Owner User",
                                "email", "owner@gastrohub.com",
                                "login", "owner",
                                "password", "Senha@123",
                                "role", "USER_OWNER"
                        ))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("/problems/email-already-exists"))
                .andExpect(jsonPath("$.title").value("Email already exists"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("The provided email is already registered."))
                .andExpect(jsonPath("$.instance").value("/users"))
                .andExpect(jsonPath("$.invalid_value").value("owner@gastrohub.com"));
    }

    @Test
    @DisplayName("Should map RoleNotFound to problem detail")
    void shouldMapRoleNotFound() throws Exception {
        given(findRoleByIdUseCase.execute(5L)).willThrow(new RoleNotFound(5L));

        mockMvc.perform(get("/roles/{id}", 5L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("/problems/role-not-found"))
                .andExpect(jsonPath("$.title").value("Role not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("The requested role was not found."))
                .andExpect(jsonPath("$.instance").value("/roles/5"))
                .andExpect(jsonPath("$.invalid_value").value(5));
    }

    @Test
    @DisplayName("Should map MenuItemNotFound to problem detail")
    void shouldMapMenuItemNotFound() throws Exception {
        given(findMenuItemByIdUseCase.execute(7L)).willThrow(new MenuItemNotFound(7L));

        mockMvc.perform(get("/menu-items/{id}", 7L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("/problems/menu-item-not-found"))
                .andExpect(jsonPath("$.title").value("Menu item not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("The requested menu item was not found."))
                .andExpect(jsonPath("$.instance").value("/menu-items/7"))
                .andExpect(jsonPath("$.invalid_value").value(7));
    }

    @Test
    @DisplayName("Should map RestaurantNotFoundException to problem detail")
    void shouldMapRestaurantNotFound() throws Exception {
        given(findRestaurantByIdUseCase.execute(12L))
                .willThrow(new RestaurantNotFoundException("Restaurant 12 was not found"));

        mockMvc.perform(get("/restaurants/{id}", 12L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("/problems/restaurant-not-found"))
                .andExpect(jsonPath("$.title").value("Restaurant not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Restaurant 12 was not found"))
                .andExpect(jsonPath("$.instance").value("/restaurants/12"));
    }

    @Test
    @DisplayName("Should map RestaurantAlreadyExistsException to problem detail")
    void shouldMapRestaurantAlreadyExists() throws Exception {
        given(createRestaurantUseCase.execute(any()))
                .willThrow(new RestaurantAlreadyExistsException("Restaurant already exists with name Burger House"));

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRestaurantJson()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("/problems/restaurant-already-exists"))
                .andExpect(jsonPath("$.title").value("Restaurant already exists"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("Restaurant already exists with name Burger House"))
                .andExpect(jsonPath("$.instance").value("/restaurants"));
    }

    @Test
    @DisplayName("Should map restaurant domain validation exceptions to problem details")
    void shouldMapRestaurantDomainValidationExceptions() throws Exception {
        given(createRestaurantUseCase.execute(any()))
                .willThrow(new InvalidRestaurantNameException("Restaurant name is invalid"))
                .willThrow(new InvalidRestaurantAddressException("Restaurant address is invalid"))
                .willThrow(new InvalidOpeningHoursException("Opening hours are invalid"))
                .willThrow(new InvalidCuisineTypeException("Cuisine type is invalid"))
                .willThrow(new InvalidUserException("User is invalid"));

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON).content(validRestaurantJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-restaurant-name"))
                .andExpect(jsonPath("$.title").value("Invalid restaurant name"))
                .andExpect(jsonPath("$.detail").value("Restaurant name is invalid"));

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON).content(validRestaurantJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-restaurant-address"))
                .andExpect(jsonPath("$.title").value("Invalid restaurant address"))
                .andExpect(jsonPath("$.detail").value("Restaurant address is invalid"));

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON).content(validRestaurantJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-opening-hours"))
                .andExpect(jsonPath("$.title").value("Invalid opening hours"))
                .andExpect(jsonPath("$.detail").value("Opening hours are invalid"));

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON).content(validRestaurantJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-cuisine-type"))
                .andExpect(jsonPath("$.title").value("Invalid cuisine type"))
                .andExpect(jsonPath("$.detail").value("Cuisine type is invalid"));

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON).content(validRestaurantJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-user"))
                .andExpect(jsonPath("$.title").value("Invalid user"))
                .andExpect(jsonPath("$.detail").value("User is invalid"));
    }

    @Test
    @DisplayName("Should map request body validation errors to problem detail")
    void shouldMapRequestBodyValidationError() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "name", "",
                                "description", "A".repeat(256)
                        ))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/validation-error"))
                .andExpect(jsonPath("$.title").value("Validation error"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("One or more request fields are invalid"))
                .andExpect(jsonPath("$.instance").value("/roles"))
                .andExpect(jsonPath("$.errors.name").value("Name is required"))
                .andExpect(jsonPath("$.errors.description").value("Description must be at most 255 characters"));
    }

    @Test
    @DisplayName("Should map unreadable request body to problem detail")
    void shouldMapUnreadableRequestBody() throws Exception {
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Burger House\",\"cuisineType\":\"UNKNOWN\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-request-data"))
                .andExpect(jsonPath("$.title").value("Invalid request data"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("The request body or parameters are invalid"))
                .andExpect(jsonPath("$.instance").value("/restaurants"));
    }

    @Test
    @DisplayName("Should map path variable type mismatch to problem detail")
    void shouldMapPathVariableTypeMismatch() throws Exception {
        mockMvc.perform(get("/users/{id}", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-request-data"))
                .andExpect(jsonPath("$.title").value("Invalid request data"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("The request body or parameters are invalid"))
                .andExpect(jsonPath("$.instance").value("/users/abc"));
    }

    @Test
    @DisplayName("Should map unexpected exceptions to problem detail")
    void shouldMapUnexpectedError() throws Exception {
        willThrow(new IllegalStateException("Unexpected failure"))
                .given(deleteRestaurantUseCase)
                .execute(3L);

        mockMvc.perform(delete("/restaurants/{id}", 3L))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.type").value("/problems/unexpected-internal-error"))
                .andExpect(jsonPath("$.title").value("Unexpected internal error"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.detail").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.instance").value("/restaurants/3"));
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

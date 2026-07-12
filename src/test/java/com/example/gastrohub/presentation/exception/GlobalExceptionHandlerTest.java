package com.example.gastrohub.presentation.exception;

import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import com.example.gastrohub.domain.user.UserRole;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleUserNotFound() {
        var response = handler.handleUserNotFound(new UserNotFound(1L), request("/users/1"));

        assertProblem(response.getBody(), HttpStatus.NOT_FOUND, "User not found", "/problems/user-not-found");
        assertThat(response.getBody().getProperties()).containsEntry("invalid_value", 1L);
    }

    @Test
    void shouldHandleMenuItemNotFound() {
        var response = handler.handleMenuItemNotFound(new MenuItemNotFound(2L), request("/menu-items/2"));

        assertProblem(response.getBody(), HttpStatus.NOT_FOUND, "Menu item not found", "/problems/menu-item-not-found");
        assertThat(response.getBody().getProperties()).containsEntry("invalid_value", 2L);
    }

    @Test
    void shouldHandleRestaurantNotFound() {
        var response = handler.handleRestaurantNotFound(new RestaurantNotFound(3L), request("/restaurants/3"));

        assertProblem(response.getBody(), HttpStatus.NOT_FOUND, "Restaurant not found", "/problems/restaurant-not-found");
        assertThat(response.getBody().getProperties()).containsEntry("invalid_value", 3L);
    }

    @Test
    void shouldHandleRoleNotFound() {
        var response = handler.handleRoleNotFound(new RoleNotFound(4L), request("/roles/4"));

        assertProblem(response.getBody(), HttpStatus.NOT_FOUND, "Role not found", "/problems/role-not-found");
        assertThat(response.getBody().getProperties()).containsEntry("invalid_value", 4L);
    }

    @Test
    void shouldHandleEmailAlreadyExists() {
        var response = handler.handleEmailAlreadyExists(
                new EmailAlreadyExistsException("vinicius@email.com"),
                request("/users")
        );

        assertProblem(response.getBody(), HttpStatus.CONFLICT, "Email already exists", "/problems/email-already-exists");
        assertThat(response.getBody().getProperties()).containsEntry("invalid_value", "vinicius@email.com");
    }

    @Test
    void shouldHandleValidationError() throws Exception {
        var bindingResult = new BeanPropertyBindingResult(new CreateUserRequest(), "request");
        bindingResult.addError(new FieldError("request", "name", "must not be blank"));

        var exception = new MethodArgumentNotValidException(methodParameter(), bindingResult);

        var response = handler.handleValidationError(exception, request("/users"));

        assertProblem(response.getBody(), HttpStatus.BAD_REQUEST, "Validation error", "/problems/validation-error");
        assertThat(response.getBody().getProperties()).containsKey("errors");
        @SuppressWarnings("unchecked")
        var errors = (Map<String, String>) response.getBody().getProperties().get("errors");
        assertThat(errors).containsEntry("name", "must not be blank");
    }

    @Test
    void shouldHandleBadRequest() {
        var response = handler.handleBadRequest(new RuntimeException("bad request"), request("/users/id"));

        assertProblem(response.getBody(), HttpStatus.BAD_REQUEST, "Invalid request data", "/problems/invalid-request-data");
    }

    @Test
    void shouldHandleUnexpectedError() {
        var response = handler.handleUnexpectedError(new RuntimeException("boom"), request("/users"));

        assertProblem(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal error", "/problems/unexpected-internal-error");
    }

    private MockHttpServletRequest request(String uri) {
        var request = new MockHttpServletRequest();
        request.setRequestURI(uri);
        return request;
    }

    private void assertProblem(
            org.springframework.http.ProblemDetail body,
            HttpStatus status,
            String title,
            String type
    ) {
        assertThat(body).isNotNull();
        assertThat(body.getStatus()).isEqualTo(status.value());
        assertThat(body.getTitle()).isEqualTo(title);
        assertThat(body.getType().toString()).isEqualTo(type);
    }

    private MethodParameter methodParameter() throws NoSuchMethodException {
        Method method = SampleController.class.getDeclaredMethod("create", CreateUserRequest.class);
        return new MethodParameter(method, 0);
    }

    private static class SampleController {
        @SuppressWarnings("unused")
        void create(CreateUserRequest request) {
            request.setRole(UserRole.USER_CLIENT);
        }
    }
}

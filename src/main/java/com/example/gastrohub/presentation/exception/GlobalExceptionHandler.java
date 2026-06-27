package com.example.gastrohub.presentation.exception;

import com.example.gastrohub.domain.menuitem.exception.MenuItemNotFound;
import com.example.gastrohub.domain.restaurant.exception.RestaurantNotFound;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ProblemDetail> handleUserNotFound(
            UserNotFound exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "User not found",
                "The requested user was not found.",
                "/problems/user-not-found",
                request,
                exception.getUserId()
        );
    }

    @ExceptionHandler(MenuItemNotFound.class)
    public ResponseEntity<ProblemDetail> handleMenuItemNotFound(
            MenuItemNotFound exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Menu item not found",
                "The requested menu item was not found.",
                "/problems/menu-item-not-found",
                request,
                exception.getMenuItemId()
        );
    }

    @ExceptionHandler(RestaurantNotFound.class)
    public ResponseEntity<ProblemDetail> handleRestaurantNotFound(
            RestaurantNotFound exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Restaurant not found",
                "The requested restaurant was not found.",
                "/problems/restaurant-not-found",
                request,
                exception.getRestaurantId()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "Email already exists",
                "The provided email is already registered.",
                "/problems/email-already-exists",
                request,
                exception.getEmail()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationError(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        var body = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "One or more request fields are invalid"
        );
        body.setType(URI.create("/problems/validation-error"));
        body.setTitle("Validation error");
        body.setInstance(URI.create(request.getRequestURI()));
        body.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ProblemDetail> handleBadRequest(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid request data",
                "The request body or parameters are invalid",
                "/problems/invalid-request-data",
                request,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpectedError(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected internal error",
                "An unexpected error occurred",
                "/problems/unexpected-internal-error",
                request,
                null
        );
    }

    private ResponseEntity<ProblemDetail> buildResponse(
            HttpStatus status,
            String title,
            String detail,
            String type,
            HttpServletRequest request,
            Object invalidValue
    ) {
        var body = ProblemDetail.forStatusAndDetail(status, detail);
        body.setType(URI.create(type));
        body.setTitle(title);
        body.setInstance(URI.create(request.getRequestURI()));
        if (invalidValue != null) {
            body.setProperty("invalid_value", invalidValue);
        }

        return ResponseEntity.status(status).body(body);
    }
}

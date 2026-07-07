package com.example.gastrohub.presentation.exception;

import com.example.gastrohub.domain.restaurant.exception.*;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;

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

    @ExceptionHandler(InvalidCuisineTypeException.class)
    public ResponseEntity<ProblemDetail> handleInvalidCuisineTypeException(
            InvalidCuisineTypeException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid cuisine type",
                exception.getMessage(),
                "/problems/invalid-cuisine-type",
                request,
                null
        );
    }

    @ExceptionHandler(InvalidOpeningHoursException.class)
    public ResponseEntity<ProblemDetail> handleInvalidOpeningHoursException(
            InvalidOpeningHoursException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid opening hours",
                exception.getMessage(),
                "/problems/invalid-opening-hours",
                request,
                null
        );
    }

    @ExceptionHandler(InvalidRestaurantAddressException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRestaurantAddressException(
            InvalidRestaurantAddressException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid restaurant address",
                exception.getMessage(),
                "/problems/invalid-restaurant-address",
                request,
                null
        );
    }

    @ExceptionHandler(InvalidRestaurantNameException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRestaurantNameException(
            InvalidRestaurantNameException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid restaurant name",
                exception.getMessage(),
                "/problems/invalid-restaurant-name",
                request,
                null
        );
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ProblemDetail> handleInvalidUserException(
            InvalidUserException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid user",
                exception.getMessage(),
                "/problems/invalid-user",
                request,
                null
        );
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "Restaurant already exists",
                exception.getMessage(),
                "/problems/restaurant-already-exists",
                request,
                null
        );
    }

    @ExceptionHandler(RestaurantNotFoundException .class)
    public ResponseEntity<ProblemDetail> handleRestaurantNotFoundException(
            RestaurantNotFoundException  exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Restaurant not found",
                exception.getMessage(),
                "/problems/restaurant-not-found",
                request,
                null
        );
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

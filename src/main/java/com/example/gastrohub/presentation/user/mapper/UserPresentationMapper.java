package com.example.gastrohub.presentation.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.presentation.restaurant.mapper.RestaurantPresentationMapper;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;

import java.util.List;

public class UserPresentationMapper {
    private UserPresentationMapper() {
    }

    public static CreateUserInput toInput(CreateUserRequest request) {
        return CreateUserInput.builder()
                .name(request.getName())
                .email(request.getEmail())
                .login(request.getLogin())
                .password(request.getPassword())
                .roleId(request.getRoleId())
                .build();
    }

    public static UpdateUserInput toInput(UpdateUserRequest request) {
        return UpdateUserInput.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .login(request.getLogin())
                .password(request.getPassword())
                .roleId(request.getRoleId())
                .build();
    }

    public static UserResponse toResponse(UserOutput output) {
        return UserResponse.builder()
                .id(output.getId())
                .name(output.getName())
                .email(output.getEmail())
                .login(output.getLogin())
                .roleId(output.getRoleId())
                .roleName(output.getRoleName())
                .restaurants(
                        output.getRestaurants() == null
                                ? List.of()
                                : output.getRestaurants()
                                .stream()
                                .map(RestaurantPresentationMapper::toResponse)
                                .toList()
                )
                .build();
    }
}

package com.example.gastrohub.presentation.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;

public class UserPresentationMapper {
    private UserPresentationMapper() {
    }

    public static CreateUserInput toInput(CreateUserRequest request) {
        return new CreateUserInput(
                request.getName(),
                request.getEmail(),
                request.getLogin(),
                request.getPassword(),
                request.getRole()
        );
    }

    public static UpdateUserInput toInput(UpdateUserRequest request) {
        return new UpdateUserInput(
                request.getId(),
                request.getName(),
                request.getEmail(),
                request.getLogin(),
                request.getPassword(),
                request.getRole()
        );
    }

    public static UserResponse toResponse(UserOutput output) {
        return new UserResponse(
                output.getId(),
                output.getName(),
                output.getEmail(),
                output.getLogin(),
                output.getRole()
        );
    }
}

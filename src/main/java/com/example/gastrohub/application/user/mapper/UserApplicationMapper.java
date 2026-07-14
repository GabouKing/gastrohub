package com.example.gastrohub.application.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.user.User;

public class UserApplicationMapper {
    private UserApplicationMapper() {
    }

    public static User toDomain(CreateUserInput input, Role role) {
        return User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .login(input.getLogin())
                .password(input.getPassword())
                .role(role)
                .build();
    }

    public static User toDomain(UpdateUserInput input, Role role) {
        return User.builder()
                .id(input.getId())
                .name(input.getName())
                .email(input.getEmail())
                .login(input.getLogin())
                .password(input.getPassword())
                .role(role)
                .build();
    }

    public static UserOutput toOutput(User user) {
        return UserOutput.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .roleId(user.getRole() == null ? null : user.getRole().getId())
                .roleName(user.getRole() == null ? null : user.getRole().getName())
                .restaurants(user.getRestaurants())
                .build();
    }
}

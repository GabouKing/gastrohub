package com.example.gastrohub.application.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.domain.user.User;

public class UserApplicationMapper {
    private UserApplicationMapper() {
    }

    public static User toDomain(CreateUserInput input) {
        return new User(
                null,
                input.getName(),
                input.getEmail(),
                input.getLogin(),
                input.getPassword(),
                input.getRole()
        );
    }

    public static User toDomain(UpdateUserInput input) {
        return new User(
                input.getId(),
                input.getName(),
                input.getEmail(),
                input.getLogin(),
                input.getPassword(),
                input.getRole()
        );
    }

    public static UserOutput toOutput(User user) {
        return new UserOutput(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getRole().name()
        );
    }
}

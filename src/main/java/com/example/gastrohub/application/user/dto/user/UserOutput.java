package com.example.gastrohub.application.user.dto.user;

import com.example.gastrohub.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserOutput {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String role;

    public static UserOutput from(User user) {
        return new UserOutput(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getRole().name()
        );
    }
}
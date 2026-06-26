package com.example.gastrohub.presentation.user.response;

import com.example.gastrohub.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String login;
    private Integer role;
}

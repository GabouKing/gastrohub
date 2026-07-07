package com.example.gastrohub.application.user.dto.user;

import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CreateUserInput {
    private String name;
    private String email;
    private String login;
    private String password;
    private UserRole role;
}

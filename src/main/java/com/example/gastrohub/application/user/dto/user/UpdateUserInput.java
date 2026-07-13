package com.example.gastrohub.application.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateUserInput {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private Long roleId;
}

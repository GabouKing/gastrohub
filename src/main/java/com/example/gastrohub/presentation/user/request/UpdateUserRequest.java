package com.example.gastrohub.presentation.user.request;

import com.example.gastrohub.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private Long id;
    private String name;
    private String email;
    private String login;
    private UserRole role;
    private String password;
}

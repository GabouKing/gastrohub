package com.example.gastrohub.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserRole role;

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(UserRole role) {
        this.role = role;
    }
}

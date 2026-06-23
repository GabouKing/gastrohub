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
    private Long roleId;

    public void changeEmail(String email) {
        // validacoes de negocio
        this.email = email;
    }
}

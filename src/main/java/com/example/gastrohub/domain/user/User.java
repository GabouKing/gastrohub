package com.example.gastrohub.domain.user;

import com.example.gastrohub.domain.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private UserRole role;

    @Builder.Default
    private List<Restaurant> restaurants = new ArrayList<>();

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(UserRole role) {
        this.role = role;
    }
}

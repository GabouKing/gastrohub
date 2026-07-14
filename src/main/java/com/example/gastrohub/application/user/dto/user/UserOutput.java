package com.example.gastrohub.application.user.dto.user;

import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserOutput {
    private Long id;
    private String name;
    private String email;
    private String login;
    private Long roleId;
    private String roleName;
    private List<Restaurant>  restaurants;

    public static UserOutput from(User user) {
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

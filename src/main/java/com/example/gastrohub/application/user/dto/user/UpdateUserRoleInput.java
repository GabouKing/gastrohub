package com.example.gastrohub.application.user.dto.user;

import com.example.gastrohub.domain.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRoleInput {
    private Long userId;
    private UserRole role;
}
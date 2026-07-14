package com.example.gastrohub.application.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRoleInput {
    private Long userId;
    private Long roleId;
}

package com.example.gastrohub.presentation.role.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
}
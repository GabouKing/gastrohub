package com.example.gastrohub.application.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRoleInput {
    private Long id;
    private String name;
    private String description;
}
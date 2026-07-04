package com.example.gastrohub.application.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoleInput {
    private String name;
    private String description;
}
package com.example.gastrohub.domain.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Role {
    private Long id;
    private String name;
    private String description;
}
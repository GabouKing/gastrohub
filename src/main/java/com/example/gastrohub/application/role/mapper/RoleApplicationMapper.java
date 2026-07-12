package com.example.gastrohub.application.role.mapper;

import com.example.gastrohub.application.role.dto.CreateRoleInput;
import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.dto.UpdateRoleInput;
import com.example.gastrohub.domain.role.Role;

public class RoleApplicationMapper {
    private RoleApplicationMapper() {}

    public static Role toDomain(CreateRoleInput input) {
        return new Role(null, input.getName(), input.getDescription());
    }

    public static Role toDomain(UpdateRoleInput input) {
        return new Role(input.getId(), input.getName(), input.getDescription());
    }

    public static RoleOutput toOutput(Role role) {
        return new RoleOutput(role.getId(), role.getName(), role.getDescription());
    }
}
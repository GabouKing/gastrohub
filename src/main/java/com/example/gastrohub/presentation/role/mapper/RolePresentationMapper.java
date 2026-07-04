package com.example.gastrohub.presentation.role.mapper;

import com.example.gastrohub.application.role.dto.CreateRoleInput;
import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.dto.UpdateRoleInput;
import com.example.gastrohub.presentation.role.request.CreateRoleRequest;
import com.example.gastrohub.presentation.role.request.UpdateRoleRequest;
import com.example.gastrohub.presentation.role.response.RoleResponse;

public class RolePresentationMapper {
    private RolePresentationMapper() {}

    public static CreateRoleInput toInput(CreateRoleRequest request) {
        return new CreateRoleInput(request.getName(), request.getDescription());
    }

    public static UpdateRoleInput toInput(Long id, UpdateRoleRequest request) {
        return new UpdateRoleInput(id, request.getName(), request.getDescription());
    }

    public static RoleResponse toResponse(RoleOutput output) {
        return new RoleResponse(output.getId(), output.getName(), output.getDescription());
    }
}
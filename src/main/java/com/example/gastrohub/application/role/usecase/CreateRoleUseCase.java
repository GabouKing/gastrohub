package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.CreateRoleInput;
import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.mapper.RoleApplicationMapper;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.springframework.stereotype.Service;

@Service
public class CreateRoleUseCase {
    private final RoleGateway roleGateway;

    public CreateRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleOutput execute(CreateRoleInput input) {
        if (roleGateway.existsByName(input.getName())) {
            throw new RoleNotFound("Role already exists: " + input.getName());
        }

        var role = RoleApplicationMapper.toDomain(input);
        var savedRole = roleGateway.save(role);
        return RoleApplicationMapper.toOutput(savedRole);
    }
}
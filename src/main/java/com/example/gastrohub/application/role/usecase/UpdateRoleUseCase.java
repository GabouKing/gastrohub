package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.dto.UpdateRoleInput;
import com.example.gastrohub.application.role.mapper.RoleApplicationMapper;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateRoleUseCase {
    private final RoleGateway roleGateway;

    public UpdateRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleOutput execute(UpdateRoleInput input) {
        var role = roleGateway.findById(input.getId())
                .orElseThrow(() -> new RoleNotFound(input.getId()));

        if (!role.getName().equals(input.getName()) && roleGateway.existsByName(input.getName())) {
            throw new RoleNotFound("Role already exists: " + input.getName());
        }

        var updatedRole = roleGateway.save(RoleApplicationMapper.toDomain(input));
        return RoleApplicationMapper.toOutput(updatedRole);
    }
}
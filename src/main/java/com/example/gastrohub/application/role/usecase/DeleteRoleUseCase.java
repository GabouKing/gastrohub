package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoleUseCase {
    private final RoleGateway roleGateway;

    public DeleteRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public void execute(Long id) {
        if (!roleGateway.existsById(id)) {
            throw new RoleNotFound(id);
        }
        roleGateway.deleteById(id);
    }
}
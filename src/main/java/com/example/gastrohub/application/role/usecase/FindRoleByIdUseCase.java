package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.mapper.RoleApplicationMapper;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.springframework.stereotype.Service;

@Service
public class FindRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public FindRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleOutput execute(Long id) {
        var role = roleGateway.findById(id)
                .orElseThrow(() -> new RoleNotFound(id));
        return RoleApplicationMapper.toOutput(role);
    }
}
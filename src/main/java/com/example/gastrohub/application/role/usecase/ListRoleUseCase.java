package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.mapper.RoleApplicationMapper;
import com.example.gastrohub.domain.role.RoleGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRoleUseCase {
    private final RoleGateway roleGateway;

    public ListRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public List<RoleOutput> execute() {
        return roleGateway.findAll().stream()
                .map(RoleApplicationMapper::toOutput)
                .toList();
    }
}
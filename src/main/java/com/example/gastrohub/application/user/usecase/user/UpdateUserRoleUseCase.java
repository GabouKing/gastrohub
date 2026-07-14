package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserRoleUseCase {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    public UpdateUserRoleUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
    }

    public void execute(UpdateUserRoleInput input) {
        var user = userGateway.findById(input.getUserId())
                .orElseThrow(() -> new UserNotFound(input.getUserId()));

        var role = roleGateway.findById(input.getRoleId())
                .orElseThrow(() -> new RoleNotFound(input.getRoleId()));

        user.changeRole(role);
        userGateway.save(user);
    }
}

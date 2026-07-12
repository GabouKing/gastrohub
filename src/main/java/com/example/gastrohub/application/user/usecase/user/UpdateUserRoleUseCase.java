package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserRoleUseCase {
    private final UserGateway userGateway;

    public UpdateUserRoleUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(UpdateUserRoleInput input) {
        var user = userGateway.findById(input.getUserId())
                .orElseThrow(() -> new UserNotFound(input.getUserId()));

        user.changeRole(input.getRole());
        userGateway.save(user);
    }
}
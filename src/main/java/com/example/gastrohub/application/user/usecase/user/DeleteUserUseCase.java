package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.domain.user.UserGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private final UserGateway userGateway;
    public DeleteUserUseCase(UserGateway userGateway) { this.userGateway = userGateway; }

    public void execute(Long id) {
        userGateway.deleteById(id);
    }
}

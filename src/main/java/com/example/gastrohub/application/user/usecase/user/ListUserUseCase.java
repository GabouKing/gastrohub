package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.mapper.UserApplicationMapper;
import com.example.gastrohub.domain.user.UserGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUserUseCase {
    private final UserGateway userGateway;

    public ListUserUseCase(UserGateway userGateway) { this.userGateway = userGateway; }

    public List<UserOutput> execute() {
        return userGateway.findAll().stream().map(UserApplicationMapper::toOutput).toList();
    }
}

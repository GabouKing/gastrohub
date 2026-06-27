package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.mapper.UserApplicationMapper;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public class FindUserByIdUseCase {
    private final UserGateway userGateway;

    public FindUserByIdUseCase(UserGateway userGateway) { this.userGateway = userGateway; }

    public UserOutput execute(Long id) {
        var user = userGateway.findById(id)
                .orElseThrow(() -> new UserNotFound(id));

        return UserApplicationMapper.toOutput(user);
    }
}

package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.mapper.UserApplicationMapper;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) { this.userGateway = userGateway; }

    public UserOutput execute(UpdateUserInput input) {
        userGateway.findById(input.getId())
                .orElseThrow(() -> new UserNotFound(input.getId()));

        userGateway.findByEmail(input.getEmail())
                .filter(user -> !user.getId().equals(input.getId()))
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException(input.getEmail());
                });

        var userToUpload = UserApplicationMapper.toDomain(input);
        var userUpdated = userGateway.save(userToUpload);

        return UserApplicationMapper.toOutput(userUpdated);

    }
}

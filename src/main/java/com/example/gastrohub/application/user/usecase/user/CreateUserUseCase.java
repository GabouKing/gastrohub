package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.mapper.UserApplicationMapper;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserOutput execute(CreateUserInput input) {
        if(userGateway.findByEmail(input.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException(input.getEmail());
        }

        var user = UserApplicationMapper.toDomain(input);
        var savedUser = userGateway.save(user);

        return UserApplicationMapper.toOutput(savedUser);
    }
}

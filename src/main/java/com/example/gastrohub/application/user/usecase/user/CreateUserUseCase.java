package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.mapper.UserApplicationMapper;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    public CreateUserUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
    }

    public UserOutput execute(CreateUserInput input) {
        if(userGateway.findByEmail(input.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException(input.getEmail());
        }

        var role = roleGateway.findById(input.getRoleId())
                .orElseThrow(() -> new RoleNotFound(input.getRoleId()));

        var user = UserApplicationMapper.toDomain(input, role);
        var savedUser = userGateway.save(user);

        return UserApplicationMapper.toOutput(savedUser);
    }
}

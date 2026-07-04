package com.example.gastrohub.presentation.user;

import com.example.gastrohub.application.user.usecase.user.*;
import com.example.gastrohub.presentation.user.mapper.UserPresentationMapper;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRoleRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserRoleUseCase updateUserRoleUseCase;
    private final ListUserUseCase listUserUseCase;
    private final FindUserByIdUseCase  findUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        var input = UserPresentationMapper.toInput(createUserRequest);
        var output = createUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) {
        updateUserRequest.setId(id);
        var input = UserPresentationMapper.toInput(updateUserRequest);
        var output = updateUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> allUsers() {
        var response = listUserUseCase.execute()
                .stream()
                .map(UserPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> user(@PathVariable Long id) {
        var output = findUserByIdUseCase.execute(id);
        var response = UserPresentationMapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {
        var input = new com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput(id, request.getRole());
        updateUserRoleUseCase.execute(input);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

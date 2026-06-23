package com.example.gastrohub.presentation.user;

import com.example.gastrohub.application.user.usecase.user.*;
import com.example.gastrohub.presentation.user.mapper.UserPresentationMapper;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final FindUserByIdUseCase  findUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        var input = UserPresentationMapper.toInput(createUserRequest);
        var output = createUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        var input = UserPresentationMapper.toInput(updateUserRequest);
        var output = updateUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(listUserUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> user(@PathVariable Long id) {
        var output = findUserByIdUseCase.execute(id);
        var response = UserPresentationMapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

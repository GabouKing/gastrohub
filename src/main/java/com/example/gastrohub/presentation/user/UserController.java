package com.example.gastrohub.presentation.user;

import com.example.gastrohub.application.user.usecase.user.*;
import com.example.gastrohub.presentation.user.docs.UserControllerDocs;
import com.example.gastrohub.presentation.user.mapper.UserPresentationMapper;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ListUserUseCase listUserUseCase;
    private final FindUserByIdUseCase  findUserByIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    @Override
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        var input = UserPresentationMapper.toInput(createUserRequest);
        var output = createUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        updateUserRequest.setId(id);
        var input = UserPresentationMapper.toInput(updateUserRequest);
        var output = updateUserUseCase.execute(input);
        var response = UserPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<UserResponse>> allUsers() {
        var response = listUserUseCase.execute()
                .stream()
                .map(UserPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponse> user(@PathVariable Long id) {
        var output = findUserByIdUseCase.execute(id);
        var response = UserPresentationMapper.toResponse(output);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

package com.example.gastrohub.presentation.role;

import com.example.gastrohub.application.role.usecase.*;
import com.example.gastrohub.presentation.role.mapper.RolePresentationMapper;
import com.example.gastrohub.presentation.role.request.CreateRoleRequest;
import com.example.gastrohub.presentation.role.request.UpdateRoleRequest;
import com.example.gastrohub.presentation.role.response.RoleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final CreateRoleUseCase createRoleUseCase;
    private final FindRoleByIdUseCase findRoleByIdUseCase;
    private final ListRoleUseCase listRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        var input = RolePresentationMapper.toInput(request);
        var output = createRoleUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(RolePresentationMapper.toResponse(output));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> listRoles() {
        var response = listRoleUseCase.execute().stream()
                .map(RolePresentationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findRoleById(@PathVariable Long id) {
        var output = findRoleByIdUseCase.execute(id);
        return ResponseEntity.ok(RolePresentationMapper.toResponse(output));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request
    ) {
        var input = RolePresentationMapper.toInput(id, request);
        var output = updateRoleUseCase.execute(input);
        return ResponseEntity.ok(RolePresentationMapper.toResponse(output));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        deleteRoleUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
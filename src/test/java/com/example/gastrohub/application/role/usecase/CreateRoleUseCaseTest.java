package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.CreateRoleInput;
import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoleUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private CreateRoleUseCase createRoleUseCase;

    private CreateRoleInput input;

    @BeforeEach
    void setUp() {
        input = new CreateRoleInput("USER_ADMIN", "Administrator");
    }

    @Test
    @DisplayName("Should create role successfully")
    void shouldCreateRoleSuccessfully() {
        when(roleGateway.existsByName(input.getName())).thenReturn(false);
        when(roleGateway.save(any(Role.class))).thenAnswer(invocation -> {
            Role r = invocation.getArgument(0);
            return new Role(1L, r.getName(), r.getDescription());
        });

        RoleOutput output = createRoleUseCase.execute(input);

        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("USER_ADMIN", output.getName()),
                () -> assertEquals("Administrator", output.getDescription())
        );

        verify(roleGateway).existsByName(input.getName());
        verify(roleGateway).save(any(Role.class));
        verifyNoMoreInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should throw exception when role name already exists")
    void shouldThrowExceptionWhenRoleNameAlreadyExists() {
        when(roleGateway.existsByName(input.getName())).thenReturn(true);

        assertThrows(RoleNotFound.class, () -> createRoleUseCase.execute(input));

        verify(roleGateway).existsByName(input.getName());
        verify(roleGateway, never()).save(any());
        verifyNoMoreInteractions(roleGateway);
    }
}
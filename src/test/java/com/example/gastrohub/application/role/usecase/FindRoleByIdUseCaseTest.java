package com.example.gastrohub.application.role.usecase;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindRoleByIdUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private FindRoleByIdUseCase findRoleByIdUseCase;

    private Role role;
    private Long roleId;

    @BeforeEach
    void setUp() {
        roleId = 1L;
        role = new Role(roleId, "USER_ADMIN", "Administrator");
    }

    @Test
    @DisplayName("Should return role when role exists")
    void shouldReturnRoleWhenRoleExists() {
        when(roleGateway.findById(roleId)).thenReturn(Optional.of(role));

        RoleOutput output = findRoleByIdUseCase.execute(roleId);

        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(roleId, output.getId()),
                () -> assertEquals("USER_ADMIN", output.getName()),
                () -> assertEquals("Administrator", output.getDescription())
        );

        verify(roleGateway).findById(roleId);
        verifyNoMoreInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should throw RoleNotFound when role does not exist")
    void shouldThrowRoleNotFoundWhenRoleDoesNotExist() {
        when(roleGateway.findById(roleId)).thenReturn(Optional.empty());

        RoleNotFound exception = assertThrows(
                RoleNotFound.class,
                () -> findRoleByIdUseCase.execute(roleId)
        );

        assertEquals("Role not found: 1", exception.getMessage());
        verify(roleGateway).findById(roleId);
        verifyNoMoreInteractions(roleGateway);
    }
}
package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoleUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private DeleteRoleUseCase deleteRoleUseCase;

    private Long roleId;

    @BeforeEach
    void setUp() {
        roleId = 1L;
    }

    @Test
    @DisplayName("Should delete role successfully when role exists")
    void shouldDeleteRoleSuccessfullyWhenRoleExists() {
        when(roleGateway.existsById(roleId)).thenReturn(true);

        deleteRoleUseCase.execute(roleId);

        verify(roleGateway).existsById(roleId);
        verify(roleGateway).deleteById(roleId);
        verifyNoMoreInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should throw RoleNotFound when role does not exist")
    void shouldThrowRoleNotFoundWhenRoleDoesNotExist() {
        when(roleGateway.existsById(roleId)).thenReturn(false);

        RoleNotFound exception = assertThrows(
                RoleNotFound.class,
                () -> deleteRoleUseCase.execute(roleId)
        );

        assertEquals("Role not found: 1", exception.getMessage());
        verify(roleGateway).existsById(roleId);
        verify(roleGateway, never()).deleteById(anyLong());
        verifyNoMoreInteractions(roleGateway);
    }
}
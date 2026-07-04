package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.role.RoleGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRoleUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private ListRoleUseCase listRoleUseCase;

    private List<Role> roles;

    @BeforeEach
    void setUp() {
        roles = List.of(
                new Role(1L, "USER_ADMIN", "Administrator"),
                new Role(2L, "USER_CLIENT", "Client")
        );
    }

    @Test
    @DisplayName("Should return all roles")
    void shouldReturnAllRoles() {
        when(roleGateway.findAll()).thenReturn(roles);

        List<RoleOutput> output = listRoleUseCase.execute();

        assertEquals(2, output.size());
        assertEquals("USER_ADMIN", output.get(0).getName());
        assertEquals("USER_CLIENT", output.get(1).getName());

        verify(roleGateway).findAll();
        verifyNoMoreInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should return empty list when no roles exist")
    void shouldReturnEmptyListWhenNoRolesExist() {
        when(roleGateway.findAll()).thenReturn(List.of());

        List<RoleOutput> output = listRoleUseCase.execute();

        assertTrue(output.isEmpty());
        verify(roleGateway).findAll();
        verifyNoMoreInteractions(roleGateway);
    }
}
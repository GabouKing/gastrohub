package com.example.gastrohub.application.role.usecase;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.dto.UpdateRoleInput;
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

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRoleUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private UpdateRoleUseCase updateRoleUseCase;

    private Role role;
    private UpdateRoleInput input;

    @BeforeEach
    void setUp() throws Exception {
        role = new Role(null, "USER_ADMIN", "Old description");
        setId(role, 1L);

        input = new UpdateRoleInput(1L, "USER_ADMIN", "New description");
    }

    @Test
    @DisplayName("Should update role successfully")
    void shouldUpdateRoleSuccessfully() {
        when(roleGateway.findById(input.getId())).thenReturn(Optional.of(role));
        when(roleGateway.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoleOutput output = updateRoleUseCase.execute(input);

        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("USER_ADMIN", output.getName()),
                () -> assertEquals("New description", output.getDescription())
        );

        verify(roleGateway).findById(input.getId());
        verify(roleGateway).save(any(Role.class));
        verifyNoMoreInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should throw RoleNotFound when role does not exist")
    void shouldThrowRoleNotFoundWhenRoleDoesNotExist() {
        when(roleGateway.findById(input.getId())).thenReturn(Optional.empty());

        RoleNotFound exception = assertThrows(
                RoleNotFound.class,
                () -> updateRoleUseCase.execute(input)
        );

        assertEquals("Role not found: 1", exception.getMessage());
        verify(roleGateway).findById(input.getId());
        verify(roleGateway, never()).save(any());
        verifyNoMoreInteractions(roleGateway);
    }

    private void setId(Role role, Long id) throws Exception {
        Field field = Role.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(role, id);
    }
}
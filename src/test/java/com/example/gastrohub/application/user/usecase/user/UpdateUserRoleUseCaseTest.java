package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.domain.role.exception.RoleNotFound;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserRoleUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private UpdateUserRoleUseCase updateUserRoleUseCase;

    private User user;
    private Role adminRole;
    private UpdateUserRoleInput input;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John", "john@email.com", "john", "123456", new Role(2L, "USER_CLIENT", "Cliente"), List.of());
        adminRole = new Role(1L, "USER_ADMIN", "Administrador");
        input = new UpdateUserRoleInput(1L, 1L);
    }

    @Test
    @DisplayName("Should update user role successfully")
    void shouldUpdateUserRoleSuccessfully() {
        when(userGateway.findById(input.getUserId())).thenReturn(Optional.of(user));
        when(roleGateway.findById(input.getRoleId())).thenReturn(Optional.of(adminRole));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        updateUserRoleUseCase.execute(input);

        assertEquals(adminRole, user.getRole());
        verify(userGateway).findById(input.getUserId());
        verify(roleGateway).findById(input.getRoleId());
        verify(userGateway).save(user);
        verifyNoMoreInteractions(userGateway, roleGateway);
    }

    @Test
    @DisplayName("Should throw UserNotFound when user does not exist")
    void shouldThrowUserNotFoundWhenUserDoesNotExist() {
        when(userGateway.findById(input.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> updateUserRoleUseCase.execute(input));

        verify(userGateway).findById(input.getUserId());
        verify(userGateway, never()).save(any());
        verifyNoMoreInteractions(userGateway);
        verifyNoInteractions(roleGateway);
    }

    @Test
    @DisplayName("Should throw RoleNotFound when role does not exist")
    void shouldThrowRoleNotFoundWhenRoleDoesNotExist() {
        when(userGateway.findById(input.getUserId())).thenReturn(Optional.of(user));
        when(roleGateway.findById(input.getRoleId())).thenReturn(Optional.empty());

        assertThrows(RoleNotFound.class, () -> updateUserRoleUseCase.execute(input));

        verify(userGateway).findById(input.getUserId());
        verify(roleGateway).findById(input.getRoleId());
        verify(userGateway, never()).save(any());
        verifyNoMoreInteractions(userGateway, roleGateway);
    }
}

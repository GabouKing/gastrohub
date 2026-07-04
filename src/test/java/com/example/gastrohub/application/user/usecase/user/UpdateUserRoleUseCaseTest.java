package com.example.gastrohub.application.user.usecase.user;

import com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.domain.user.UserGateway;
import com.example.gastrohub.domain.user.UserRole;
import com.example.gastrohub.domain.user.exception.UserNotFound;
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
class UpdateUserRoleUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateUserRoleUseCase updateUserRoleUseCase;

    private User user;
    private UpdateUserRoleInput input;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John", "john@email.com", "john", "123456", UserRole.USER_CLIENT);
        input = new UpdateUserRoleInput(1L, UserRole.USER_ADMIN);
    }

    @Test
    @DisplayName("Should update user role successfully")
    void shouldUpdateUserRoleSuccessfully() {
        when(userGateway.findById(input.getUserId())).thenReturn(Optional.of(user));
        when(userGateway.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        updateUserRoleUseCase.execute(input);

        assertEquals(UserRole.USER_ADMIN, user.getRole());
        verify(userGateway).findById(input.getUserId());
        verify(userGateway).save(user);
        verifyNoMoreInteractions(userGateway);
    }

    @Test
    @DisplayName("Should throw UserNotFound when user does not exist")
    void shouldThrowUserNotFoundWhenUserDoesNotExist() {
        when(userGateway.findById(input.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> updateUserRoleUseCase.execute(input));

        verify(userGateway).findById(input.getUserId());
        verify(userGateway, never()).save(any());
        verifyNoMoreInteractions(userGateway);
    }
}
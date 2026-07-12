package com.example.gastrohub.presentation.user;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.usecase.user.CreateUserUseCase;
import com.example.gastrohub.application.user.usecase.user.DeleteUserUseCase;
import com.example.gastrohub.application.user.usecase.user.FindUserByIdUseCase;
import com.example.gastrohub.application.user.usecase.user.ListUserUseCase;
import com.example.gastrohub.application.user.usecase.user.UpdateUserRoleUseCase;
import com.example.gastrohub.application.user.usecase.user.UpdateUserUseCase;
import com.example.gastrohub.domain.user.UserRole;
import com.example.gastrohub.presentation.user.request.CreateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRequest;
import com.example.gastrohub.presentation.user.request.UpdateUserRoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private CreateUserUseCase createUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private UpdateUserRoleUseCase updateUserRoleUseCase;
    private ListUserUseCase listUserUseCase;
    private FindUserByIdUseCase findUserByIdUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private UserController controller;

    @BeforeEach
    void setUp() {
        createUserUseCase = mock(CreateUserUseCase.class);
        updateUserUseCase = mock(UpdateUserUseCase.class);
        updateUserRoleUseCase = mock(UpdateUserRoleUseCase.class);
        listUserUseCase = mock(ListUserUseCase.class);
        findUserByIdUseCase = mock(FindUserByIdUseCase.class);
        deleteUserUseCase = mock(DeleteUserUseCase.class);
        controller = new UserController(
                createUserUseCase,
                updateUserUseCase,
                updateUserRoleUseCase,
                listUserUseCase,
                findUserByIdUseCase,
                deleteUserUseCase
        );
    }

    @Test
    void shouldCreateUser() {
        var request = new CreateUserRequest(
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                UserRole.USER_OWNER
        );
        when(createUserUseCase.execute(org.mockito.ArgumentMatchers.any(CreateUserInput.class)))
                .thenReturn(output());

        var response = controller.createUser(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("vinicius@email.com");

        var captor = ArgumentCaptor.forClass(CreateUserInput.class);
        verify(createUserUseCase).execute(captor.capture());
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.USER_OWNER);
    }

    @Test
    void shouldUpdateUser() {
        var request = new UpdateUserRequest(
                null,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                UserRole.USER_OWNER,
                "123456"
        );
        when(updateUserUseCase.execute(org.mockito.ArgumentMatchers.any(UpdateUserInput.class)))
                .thenReturn(output());

        var response = controller.updateUser(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLogin()).isEqualTo("vinicius");

        var captor = ArgumentCaptor.forClass(UpdateUserInput.class);
        verify(updateUserUseCase).execute(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
        assertThat(request.getId()).isEqualTo(1L);
    }

    @Test
    void shouldListUsers() {
        when(listUserUseCase.execute()).thenReturn(List.of(output()));

        var response = controller.allUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().getRole()).isEqualTo("USER_OWNER");
        verify(listUserUseCase).execute();
    }

    @Test
    void shouldFindUserById() {
        when(findUserByIdUseCase.execute(1L)).thenReturn(output());

        var response = controller.user(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        verify(findUserByIdUseCase).execute(1L);
    }

    @Test
    void shouldUpdateUserRole() {
        var request = new UpdateUserRoleRequest(UserRole.USER_ADMIN);

        var response = controller.updateUserRole(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();

        var captor = ArgumentCaptor.forClass(com.example.gastrohub.application.user.dto.user.UpdateUserRoleInput.class);
        verify(updateUserRoleUseCase).execute(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo(1L);
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.USER_ADMIN);
    }

    @Test
    void shouldDeleteUser() {
        var response = controller.deleteUser(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        verify(deleteUserUseCase).execute(1L);
    }

    private UserOutput output() {
        return new UserOutput(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "USER_OWNER"
        );
    }
}

package com.example.gastrohub.presentation.role;

import com.example.gastrohub.application.role.dto.CreateRoleInput;
import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.dto.UpdateRoleInput;
import com.example.gastrohub.application.role.usecase.CreateRoleUseCase;
import com.example.gastrohub.application.role.usecase.DeleteRoleUseCase;
import com.example.gastrohub.application.role.usecase.FindRoleByIdUseCase;
import com.example.gastrohub.application.role.usecase.ListRoleUseCase;
import com.example.gastrohub.application.role.usecase.UpdateRoleUseCase;
import com.example.gastrohub.presentation.role.request.CreateRoleRequest;
import com.example.gastrohub.presentation.role.request.UpdateRoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    private CreateRoleUseCase createRoleUseCase;
    private FindRoleByIdUseCase findRoleByIdUseCase;
    private ListRoleUseCase listRoleUseCase;
    private UpdateRoleUseCase updateRoleUseCase;
    private DeleteRoleUseCase deleteRoleUseCase;
    private RoleController controller;

    @BeforeEach
    void setUp() {
        createRoleUseCase = mock(CreateRoleUseCase.class);
        findRoleByIdUseCase = mock(FindRoleByIdUseCase.class);
        listRoleUseCase = mock(ListRoleUseCase.class);
        updateRoleUseCase = mock(UpdateRoleUseCase.class);
        deleteRoleUseCase = mock(DeleteRoleUseCase.class);
        controller = new RoleController(
                createRoleUseCase,
                findRoleByIdUseCase,
                listRoleUseCase,
                updateRoleUseCase,
                deleteRoleUseCase
        );
    }

    @Test
    void shouldCreateRole() {
        var request = new CreateRoleRequest("OWNER", "Dono do restaurante");
        when(createRoleUseCase.execute(org.mockito.ArgumentMatchers.any(CreateRoleInput.class)))
                .thenReturn(output());

        var response = controller.createRole(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("OWNER");

        var captor = ArgumentCaptor.forClass(CreateRoleInput.class);
        verify(createRoleUseCase).execute(captor.capture());
        assertThat(captor.getValue().getDescription()).isEqualTo("Dono do restaurante");
    }

    @Test
    void shouldListRoles() {
        when(listRoleUseCase.execute()).thenReturn(List.of(output()));

        var response = controller.listRoles();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().getFirst().getId()).isEqualTo(1L);
        verify(listRoleUseCase).execute();
    }

    @Test
    void shouldFindRoleById() {
        when(findRoleByIdUseCase.execute(1L)).thenReturn(output());

        var response = controller.findRoleById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo("Dono do restaurante");
        verify(findRoleByIdUseCase).execute(1L);
    }

    @Test
    void shouldUpdateRole() {
        var request = new UpdateRoleRequest("OWNER", "Dono do restaurante");
        when(updateRoleUseCase.execute(org.mockito.ArgumentMatchers.any(UpdateRoleInput.class)))
                .thenReturn(output());

        var response = controller.updateRole(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("OWNER");

        var captor = ArgumentCaptor.forClass(UpdateRoleInput.class);
        verify(updateRoleUseCase).execute(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void shouldDeleteRole() {
        var response = controller.deleteRole(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(deleteRoleUseCase).execute(1L);
    }

    private RoleOutput output() {
        return new RoleOutput(1L, "OWNER", "Dono do restaurante");
    }
}

package com.example.gastrohub.presentation.user;

import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.application.user.usecase.user.CreateUserUseCase;
import com.example.gastrohub.application.user.usecase.user.DeleteUserUseCase;
import com.example.gastrohub.application.user.usecase.user.FindUserByIdUseCase;
import com.example.gastrohub.application.user.usecase.user.ListUserUseCase;
import com.example.gastrohub.application.user.usecase.user.UpdateUserRoleUseCase;
import com.example.gastrohub.application.user.usecase.user.UpdateUserUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private ListUserUseCase listUserUseCase;

    @MockitoBean
    private FindUserByIdUseCase findUserByIdUseCase;

    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockitoBean
    private UpdateUserRoleUseCase updateUserRoleUseCase;

    @Test
    @DisplayName("Should create user through MockMvc")
    void shouldCreateUser() throws Exception {
        given(createUserUseCase.execute(any())).willReturn(userOutput());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Owner User"))
                .andExpect(jsonPath("$.email").value("owner@gastrohub.com"))
                .andExpect(jsonPath("$.login").value("owner"))
                .andExpect(jsonPath("$.roleId").value(3))
                .andExpect(jsonPath("$.roleName").value("USER_OWNER"))
                .andExpect(jsonPath("$.restaurants").isArray());
    }

    @Test
    @DisplayName("Should find user by id through MockMvc")
    void shouldFindUserById() throws Exception {
        given(findUserByIdUseCase.execute(1L)).willReturn(userOutput());

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("owner@gastrohub.com"));
    }

    @Test
    @DisplayName("Should list users through MockMvc")
    void shouldListUsers() throws Exception {
        given(listUserUseCase.execute()).willReturn(List.of(userOutput()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].login").value("owner"));
    }

    @Test
    @DisplayName("Should update user through MockMvc")
    void shouldUpdateUser() throws Exception {
        given(updateUserUseCase.execute(any())).willReturn(userOutput());

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validUserJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Owner User"));
    }

    @Test
    @DisplayName("Should delete user through MockMvc")
    void shouldDeleteUser() throws Exception {
        doNothing().when(deleteUserUseCase).execute(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("Content-Type"))
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Should update user role through MockMvc")
    void shouldUpdateUserRole() throws Exception {
        doNothing().when(updateUserRoleUseCase).execute(any());

        mockMvc.perform(patch("/users/{id}/role", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("roleId", 1))))
                .andExpect(status().isOk())
                .andExpect(header().doesNotExist("Content-Type"))
                .andExpect(content().string(""));
    }

    private UserOutput userOutput() {
        return new UserOutput(
                1L,
                "Owner User",
                "owner@gastrohub.com",
                "owner",
                3L,
                "USER_OWNER",
                List.of()
        );
    }

    private String validUserJson() throws Exception {
        return json(Map.of(
                "name", "Owner User",
                "email", "owner@gastrohub.com",
                "login", "owner",
                "password", "Senha@123",
                "roleId", 3
        ));
    }

    private String json(Map<String, Object> body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }
}

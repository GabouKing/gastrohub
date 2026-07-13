package com.example.gastrohub.presentation.role;

import com.example.gastrohub.application.role.dto.RoleOutput;
import com.example.gastrohub.application.role.usecase.CreateRoleUseCase;
import com.example.gastrohub.application.role.usecase.DeleteRoleUseCase;
import com.example.gastrohub.application.role.usecase.FindRoleByIdUseCase;
import com.example.gastrohub.application.role.usecase.ListRoleUseCase;
import com.example.gastrohub.application.role.usecase.UpdateRoleUseCase;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
class RoleControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateRoleUseCase createRoleUseCase;

    @MockitoBean
    private FindRoleByIdUseCase findRoleByIdUseCase;

    @MockitoBean
    private ListRoleUseCase listRoleUseCase;

    @MockitoBean
    private UpdateRoleUseCase updateRoleUseCase;

    @MockitoBean
    private DeleteRoleUseCase deleteRoleUseCase;

    @Test
    @DisplayName("Should create role through MockMvc")
    void shouldCreateRole() throws Exception {
        given(createRoleUseCase.execute(any())).willReturn(roleOutput());

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRoleJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OWNER"))
                .andExpect(jsonPath("$.description").value("Dono do restaurante"));
    }

    @Test
    @DisplayName("Should find role by id through MockMvc")
    void shouldFindRoleById() throws Exception {
        given(findRoleByIdUseCase.execute(1L)).willReturn(roleOutput());

        mockMvc.perform(get("/roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OWNER"));
    }

    @Test
    @DisplayName("Should list roles through MockMvc")
    void shouldListRoles() throws Exception {
        given(listRoleUseCase.execute()).willReturn(List.of(roleOutput()));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].description").value("Dono do restaurante"));
    }

    @Test
    @DisplayName("Should update role through MockMvc")
    void shouldUpdateRole() throws Exception {
        given(updateRoleUseCase.execute(any())).willReturn(roleOutput());

        mockMvc.perform(put("/roles/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validRoleJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OWNER"));
    }

    @Test
    @DisplayName("Should delete role through MockMvc")
    void shouldDeleteRole() throws Exception {
        doNothing().when(deleteRoleUseCase).execute(1L);

        mockMvc.perform(delete("/roles/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(header().doesNotExist("Content-Type"))
                .andExpect(content().string(""));
    }

    private RoleOutput roleOutput() {
        return new RoleOutput(1L, "OWNER", "Dono do restaurante");
    }

    private String validRoleJson() throws Exception {
        return json(Map.of(
                "name", "OWNER",
                "description", "Dono do restaurante"
        ));
    }

    private String json(Map<String, Object> body) throws Exception {
        return objectMapper.writeValueAsString(body);
    }
}

package com.example.gastrohub.application.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserApplicationMapperTest {

    @Test
    void shouldMapCreateInputToDomain() {
        var input = new CreateUserInput(
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                3L
        );
        var role = ownerRole();

        var user = UserApplicationMapper.toDomain(input, role);

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("Vinicius");
        assertThat(user.getEmail()).isEqualTo("vinicius@email.com");
        assertThat(user.getLogin()).isEqualTo("vinicius");
        assertThat(user.getPassword()).isEqualTo("123456");
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    void shouldMapUpdateInputToDomain() {
        var input = new UpdateUserInput(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                1L
        );
        var role = adminRole();

        var user = UserApplicationMapper.toDomain(input, role);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    void shouldMapDomainToOutput() {
        var user = user();

        var output = UserApplicationMapper.toOutput(user);

        assertThat(output.getId()).isEqualTo(1L);
        assertThat(output.getName()).isEqualTo("Vinicius");
        assertThat(output.getEmail()).isEqualTo("vinicius@email.com");
        assertThat(output.getLogin()).isEqualTo("vinicius");
        assertThat(output.getRoleId()).isEqualTo(3L);
        assertThat(output.getRoleName()).isEqualTo("USER_OWNER");
    }

    @Test
    void shouldCreateOutputFromDomain() {
        var output = UserOutput.from(user());

        assertThat(output.getId()).isEqualTo(1L);
        assertThat(output.getRoleId()).isEqualTo(3L);
        assertThat(output.getRoleName()).isEqualTo("USER_OWNER");
    }

    private User user() {
        return new User(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                ownerRole(),
                List.of()
        );
    }

    private Role ownerRole() {
        return new Role(3L, "USER_OWNER", "Dono de restaurante");
    }

    private Role adminRole() {
        return new Role(1L, "USER_ADMIN", "Administrador");
    }
}

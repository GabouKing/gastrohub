package com.example.gastrohub.application.user.mapper;

import com.example.gastrohub.application.user.dto.user.CreateUserInput;
import com.example.gastrohub.application.user.dto.user.UpdateUserInput;
import com.example.gastrohub.application.user.dto.user.UserOutput;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.domain.user.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserApplicationMapperTest {

    @Test
    void shouldMapCreateInputToDomain() {
        var input = new CreateUserInput(
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                UserRole.USER_OWNER
        );

        var user = UserApplicationMapper.toDomain(input);

        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isEqualTo("Vinicius");
        assertThat(user.getEmail()).isEqualTo("vinicius@email.com");
        assertThat(user.getLogin()).isEqualTo("vinicius");
        assertThat(user.getPassword()).isEqualTo("123456");
        assertThat(user.getRole()).isEqualTo(UserRole.USER_OWNER);
    }

    @Test
    void shouldMapUpdateInputToDomain() {
        var input = new UpdateUserInput(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                UserRole.USER_ADMIN
        );

        var user = UserApplicationMapper.toDomain(input);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getRole()).isEqualTo(UserRole.USER_ADMIN);
    }

    @Test
    void shouldMapDomainToOutput() {
        var user = user();

        var output = UserApplicationMapper.toOutput(user);

        assertThat(output.getId()).isEqualTo(1L);
        assertThat(output.getName()).isEqualTo("Vinicius");
        assertThat(output.getEmail()).isEqualTo("vinicius@email.com");
        assertThat(output.getLogin()).isEqualTo("vinicius");
        assertThat(output.getRole()).isEqualTo("USER_OWNER");
    }

    @Test
    void shouldCreateOutputFromDomain() {
        var output = UserOutput.from(user());

        assertThat(output.getId()).isEqualTo(1L);
        assertThat(output.getRole()).isEqualTo("USER_OWNER");
    }

    private User user() {
        return new User(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                UserRole.USER_OWNER
        );
    }
}

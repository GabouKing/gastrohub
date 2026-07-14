package com.example.gastrohub.domain.user;

import com.example.gastrohub.domain.role.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Should create user when all fields are valid")
    void shouldCreateUser_WhenAllFieldsAreValid() {
        var role = new Role(1L, "USER_ADMIN", "Admin");

        var user = new User(1L, "John Doe", "john@email.com", "johndoe", "123456", role, new ArrayList<>());

        assertAll(
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("John Doe", user.getName()),
                () -> assertEquals("john@email.com", user.getEmail()),
                () -> assertEquals("johndoe", user.getLogin()),
                () -> assertEquals("123456", user.getPassword()),
                () -> assertSame(role, user.getRole())
        );
    }

    @Test
    @DisplayName("Should create user using builder")
    void shouldCreateUser_UsingBuilder() {
        var role = new Role(1L, "USER_CLIENT", "Client");

        var user = User.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane@email.com")
                .login("janedoe")
                .password("654321")
                .role(role)
                .build();

        assertAll(
                () -> assertEquals("Jane Doe", user.getName()),
                () -> assertEquals("jane@email.com", user.getEmail()),
                () -> assertEquals("janedoe", user.getLogin()),
                () -> assertSame(role, user.getRole())
        );
    }

    @Test
    @DisplayName("Should initialize restaurants with empty list")
    void shouldInitializeWithEmptyRestaurants() {
        var role = new Role(1L, "USER_CLIENT", "Client");

        var user = User.builder()
                .id(1L)
                .name("John")
                .email("john@email.com")
                .login("john")
                .password("pass")
                .role(role)
                .build();

        assertNotNull(user.getRestaurants());
        assertTrue(user.getRestaurants().isEmpty());
    }

    @Test
    @DisplayName("Should change email")
    void shouldChangeEmail() {
        var role = new Role(1L, "USER_CLIENT", "Client");
        var user = new User(1L, "John", "old@email.com", "john", "pass", role, new ArrayList<>());

        user.changeEmail("new@email.com");

        assertEquals("new@email.com", user.getEmail());
    }

    @Test
    @DisplayName("Should change role")
    void shouldChangeRole() {
        var role = new Role(1L, "USER_CLIENT", "Client");
        var user = new User(1L, "John", "john@email.com", "john", "pass", role, new ArrayList<>());

        var newRole = new Role(2L, "USER_ADMIN", "Admin");
        user.changeRole(newRole);

        assertSame(newRole, user.getRole());
    }
}
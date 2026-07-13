package com.example.gastrohub.domain.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    @DisplayName("Should create role when all fields are valid")
    void shouldCreateRole_WhenAllFieldsAreValid() {
        var role = new Role(1L, "USER_ADMIN", "Administrator");

        assertAll(
                () -> assertEquals(1L, role.getId()),
                () -> assertEquals("USER_ADMIN", role.getName()),
                () -> assertEquals("Administrator", role.getDescription())
        );
    }

    @Test
    @DisplayName("Should create role with null description")
    void shouldCreateRole_WithNullDescription() {
        var role = new Role(2L, "USER_CLIENT", null);

        assertAll(
                () -> assertEquals(2L, role.getId()),
                () -> assertEquals("USER_CLIENT", role.getName()),
                () -> assertEquals(null, role.getDescription())
        );
    }
}
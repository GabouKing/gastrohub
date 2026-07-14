package com.example.gastrohub.domain.role.exception;

import lombok.Getter;

@Getter
public class RoleNotFound extends RuntimeException {
    private final Long roleId;

    public RoleNotFound(Long roleId) {
        super("Role not found: " + roleId);
        this.roleId = roleId;
    }

    public RoleNotFound(String message) {
        super(message);
        this.roleId = null;
    }
}
package com.example.gastrohub.domain.user.exception;

public class UserNotFound extends RuntimeException {
    private final Long userId;

    public UserNotFound(Long userId) {
        super("User not found");
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}

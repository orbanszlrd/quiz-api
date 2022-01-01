package com.orbanszlrd.quizapi.modules.user.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User '%s' not found!", id));
    }
}

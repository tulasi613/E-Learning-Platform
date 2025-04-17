package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
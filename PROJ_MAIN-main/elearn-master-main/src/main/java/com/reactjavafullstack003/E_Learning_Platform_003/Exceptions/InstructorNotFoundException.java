package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

public class InstructorNotFoundException extends RuntimeException {
    public InstructorNotFoundException(String message) {
        super(message);
    }
}
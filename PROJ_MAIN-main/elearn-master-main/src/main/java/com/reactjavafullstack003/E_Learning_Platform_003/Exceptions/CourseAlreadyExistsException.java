package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String message) {
        super(message);
    }

}

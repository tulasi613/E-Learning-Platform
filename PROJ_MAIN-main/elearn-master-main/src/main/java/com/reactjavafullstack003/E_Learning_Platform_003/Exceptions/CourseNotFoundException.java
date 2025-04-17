package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }

}

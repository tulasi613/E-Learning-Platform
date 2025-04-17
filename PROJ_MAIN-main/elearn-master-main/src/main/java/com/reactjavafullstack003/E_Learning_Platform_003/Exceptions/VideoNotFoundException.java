package com.reactjavafullstack003.E_Learning_Platform_003.Exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}

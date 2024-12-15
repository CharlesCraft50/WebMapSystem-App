package com.systemweb.webmapsystem;

// Custom exception for user already exists scenario
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

package com.nittan.user_authentication_service.exception;

/**
 * Exception thrown when a user is not found or authorized.
 */
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}

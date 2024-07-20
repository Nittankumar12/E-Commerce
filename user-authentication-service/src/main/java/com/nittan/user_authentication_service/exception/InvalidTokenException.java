package com.nittan.user_authentication_service.exception;

/**
 * Exception thrown when an invalid token is encountered.
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}

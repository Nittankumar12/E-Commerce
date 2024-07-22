package com.nittan.e_commerce.exception;

/**
 * Exception thrown when a user is not found or authorized.
 */
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}

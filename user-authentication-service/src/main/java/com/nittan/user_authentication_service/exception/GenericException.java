package com.nittan.user_authentication_service.exception;

/**
 * Generic exception class to handle unexpected errors.
 */
public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }
}

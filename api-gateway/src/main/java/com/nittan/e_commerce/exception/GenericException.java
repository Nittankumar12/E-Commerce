package com.nittan.e_commerce.exception;

/**
 * Generic exception class to handle unexpected errors.
 */
public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }
}

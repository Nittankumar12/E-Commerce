package com.nittan.e_commerce.exception;

/**
 * Custom exception for generic errors in the application.
 */
public class GenericeException extends RuntimeException {

    /**
     * Constructs a new GenericeException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public GenericeException(String message){
        super(message);
    }
}

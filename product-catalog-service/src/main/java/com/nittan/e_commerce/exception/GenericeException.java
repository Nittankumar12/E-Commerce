package com.nittan.e_commerce.exception;

/**
 * Generic exception for handling various runtime exceptions in the application.
 */
public class GenericeException extends RuntimeException {

    /**
     * Constructs a new GenericeException with the specified detail message.
     *
     * @param message the detail message
     */
    public GenericeException(String message){
        super(message);
    }
}

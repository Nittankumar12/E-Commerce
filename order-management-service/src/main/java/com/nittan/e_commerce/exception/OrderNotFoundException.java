package com.nittan.e_commerce.exception;

/**
 * Custom exception for Order not found errors.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs a new OrderNotFoundException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public OrderNotFoundException(String message){
        super(message);
    }
}

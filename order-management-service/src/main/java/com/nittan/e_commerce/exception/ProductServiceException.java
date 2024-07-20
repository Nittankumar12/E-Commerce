package com.nittan.e_commerce.exception;

/**
 * Custom exception for ProductService related errors.
 */
public class ProductServiceException extends RuntimeException {

    /**
     * Constructs a new ProductServiceException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ProductServiceException(String message) {
        super(message);
    }
}

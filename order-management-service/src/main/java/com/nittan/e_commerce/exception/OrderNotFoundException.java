package com.nittan.e_commerce.exception;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message){
        super(message);
    }
}

package com.nittan.user_authentication_service.exception;


// exception to handle general errors
public class GenericException extends RuntimeException{
    public GenericException(String message){
        super(message);
    }
}

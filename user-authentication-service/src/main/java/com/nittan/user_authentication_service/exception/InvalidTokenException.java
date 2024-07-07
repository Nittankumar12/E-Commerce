package com.nittan.user_authentication_service.exception;


// invalid token exception
public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){ super(message); }
}

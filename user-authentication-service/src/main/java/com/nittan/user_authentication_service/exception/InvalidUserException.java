package com.nittan.user_authentication_service.exception;


// unauthorized access exception
public class InvalidUserException extends  RuntimeException{
    public InvalidUserException(String message){ super(message); }
}

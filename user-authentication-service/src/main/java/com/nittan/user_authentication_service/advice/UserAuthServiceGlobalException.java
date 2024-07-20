package com.nittan.user_authentication_service.advice;

import com.nittan.user_authentication_service.dto.CustomeErrorResponse;
import com.nittan.user_authentication_service.dto.GlobalErrorCode;
import com.nittan.user_authentication_service.exception.GenericException;
import com.nittan.user_authentication_service.exception.InvalidTokenException;
import com.nittan.user_authentication_service.exception.InvalidUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserAuthServiceGlobalException {

    // Invalid User Exception handler
   @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> handleOrderNotFoundException(InvalidUserException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(GlobalErrorCode.ERROR_USER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();
        log.error("UserAuthServiceGlobalExceptionHandler::handleUserNotFoundException exception caught {}", exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);

    }

    // Invalid token exception handler
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleOrderNotFoundException(InvalidTokenException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(GlobalErrorCode.ERROR_INVALID_TOKEN)
                .errorMessage(exception.getMessage())
                .build();
        log.error("UserAuthServiceGlobalExceptionHandler::handleInvalidTokenException exception caught {}", exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);

    }

    // generic exception handler
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleOrderNotFoundException(GenericException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.GENERIC_ERROR)
                .errorMessage(exception.getMessage())
                .build();
        log.error("UserAuthServiceGlobalExceptionHandler::handleGenericUserAuth exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);

    }
}

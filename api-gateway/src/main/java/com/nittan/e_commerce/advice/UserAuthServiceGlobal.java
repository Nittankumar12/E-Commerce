package com.nittan.e_commerce.advice;

import com.nittan.e_commerce.exception.GenericException;
import com.nittan.e_commerce.exception.InvalidTokenException;
import com.nittan.e_commerce.exception.InvalidUserException;
import com.nittan.e_commerce.exception.UsernameNotFoundException;
import com.nittan.e_commerce.util.CustomeErrorResponse;
import com.nittan.e_commerce.util.GlobalErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserAuthServiceGlobal {
    /**
     * Exception handler for InvalidUserException.
     * Handles Unauthorized (401) status for invalid user scenarios.
     * Logs the caught exception at ERROR level.
     * @param exception The InvalidUserException instance caught
     * @return ResponseEntity with a custom error response and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<CustomeErrorResponse> handleOrderNotFoundException(InvalidUserException exception){
        // Prepare error response with HTTP status UNAUTHORIZED and specific error code
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(GlobalErrorCode.ERROR_USER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();

        // Log the caught exception at ERROR level
        log.error("UserAuthServiceGlobalExceptionHandler::handleUserNotFoundException exception caught {}", exception.getMessage());

        // Return ResponseEntity with the error response and HTTP status UNAUTHORIZED
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler for InvalidTokenException.
     * Handles Unauthorized (401) status for invalid token scenarios.
     * Logs the caught exception at ERROR level.
     * @param exception The InvalidTokenException instance caught
     * @return ResponseEntity with a custom error response and HTTP status UNAUTHORIZED
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CustomeErrorResponse> handleOrderNotFoundException(InvalidTokenException exception){
        // Prepare error response with HTTP status UNAUTHORIZED and specific error code
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .errorCode(GlobalErrorCode.ERROR_INVALID_TOKEN)
                .errorMessage(exception.getMessage())
                .build();

        // Log the caught exception at ERROR level
        log.error("UserAuthServiceGlobalExceptionHandler::handleInvalidTokenException exception caught {}", exception.getMessage());

        // Return ResponseEntity with the error response and HTTP status UNAUTHORIZED
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler for GenericException.
     * Handles Internal Server Error (500) status for generic exceptions.
     * Logs the caught exception at ERROR level.
     * @param exception The GenericException instance caught
     * @return ResponseEntity with a custom error response and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<CustomeErrorResponse> handleOrderNotFoundException(GenericException exception){
        // Prepare error response with HTTP status INTERNAL_SERVER_ERROR and generic error code
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.GENERIC_ERROR)
                .errorMessage(exception.getMessage())
                .build();

        // Log the caught exception at ERROR level
        log.error("UserAuthServiceGlobalExceptionHandler::handleGenericUserAuth exception caught {}", exception.getMessage());

        // Return ResponseEntity with the error response and HTTP status INTERNAL_SERVER_ERROR
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Exception handler for UsernameNotFound.
     * Handles Not found  for user not found exceptions.
     * Logs the caught exception at ERROR level.
     * @param exception The UsernameNotFound instance caught
     * @return ResponseEntity with a custom error response and HTTP status NOt_FOUND
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomeErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception){
        // Prepare error response with HTTP status INTERNAL_SERVER_ERROR and generic error code
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.ERROR_USER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();

        // Log the caught exception at ERROR level
        log.error("UserAuthServiceGlobalExceptionHandler::handleUsernotFound exception caught {}", exception.getMessage());

        // Return ResponseEntity with the error response and HTTP status INTERNAL_SERVER_ERROR
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}

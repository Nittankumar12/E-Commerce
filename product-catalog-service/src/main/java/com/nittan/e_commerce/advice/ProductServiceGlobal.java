package com.nittan.e_commerce.advice;

import com.nittan.e_commerce.entity.CustomeErrorResponse;
import com.nittan.e_commerce.entity.GlobalErrorCode;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for ProductService.
 */
@RestControllerAdvice
@Slf4j
public class ProductServiceGlobal {

    /**
     * Exception handler for ProductNotFoundException.
     *
     * @param exception The exception instance.
     * @return ResponseEntity containing error response.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomeErrorResponse> handleProductNotFoundException(ProductNotFoundException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.ERROR_ORDER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();
        log.error("ProductServiceGlobalExceptionHandler::handleProductNotFoundException - Exception: {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Exception handler for GenericeException (Generic exception).
     *
     * @param exception The exception instance.
     * @return ResponseEntity containing error response.
     */
    @ExceptionHandler(GenericeException.class)
    public ResponseEntity<CustomeErrorResponse> handleGenericException(Exception exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.GENERIC_ERROR)
                .errorMessage(exception.getMessage())
                .build();
        log.error("ProductServiceGlobalExceptionHandler::handleGenericException - Exception: {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}

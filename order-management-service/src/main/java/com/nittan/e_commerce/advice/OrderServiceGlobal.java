package com.nittan.e_commerce.advice;

import com.nittan.e_commerce.dto.CustomeErrorResponse;
import com.nittan.e_commerce.dto.GlobalErrorCode;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import com.nittan.e_commerce.exception.ProductServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for Order Service.
 */
@RestControllerAdvice
@Slf4j
public class OrderServiceGlobal {

    /**
     * Exception handler for OrderNotFoundException.
     * @param exception The exception instance.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<CustomeErrorResponse> handleOrderNotFoundException(OrderNotFoundException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.ERROR_ORDER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();
        log.error("OrderServiceGlobalExceptionHandler::handleOrderNotFoundException - Exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Exception handler for GenericeException (Generic exception).
     * @param exception The exception instance.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(GenericeException.class)
    public ResponseEntity<CustomeErrorResponse> handleGenericException(Exception exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.GENERIC_ERROR)
                .errorMessage(exception.getMessage())
                .build();
        log.error("OrderServiceGlobalExceptionHandler::handleGenericException - Exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Exception handler for ProductServiceException.
     * @param exception The exception instance.
     * @return ResponseEntity with error details.
     */
    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<CustomeErrorResponse> handleProductsNotFoundException(ProductServiceException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.PRODUCTS_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();
        log.error("OrderServiceGlobalExceptionHandler::handleProductsNotFoundException - Exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

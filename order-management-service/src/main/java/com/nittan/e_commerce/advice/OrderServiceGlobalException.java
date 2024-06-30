package com.nittan.e_commerce.advice;


import com.nittan.e_commerce.dto.CustomeErrorResponse;
import com.nittan.e_commerce.dto.GlobalErrorCode;
import com.nittan.e_commerce.exception.GenericeException;
import com.nittan.e_commerce.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderServiceGlobalException {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.ERROR_ORDER_NOT_FOUND)
                .errorMessage(exception.getMessage())
                .build();
        log.error("OrderServiceGlobalExceptionHandler::handleOrderNotFoundException exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);

    }

    @ExceptionHandler(GenericeException.class)
    public ResponseEntity<?> handleGenericException(Exception exception){
        CustomeErrorResponse errorResponse = CustomeErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCode.GENERIC_ERROR)
                .errorMessage(exception.getMessage())
                .build();
        log.error("OrderServiceGlobalExceptionHandler::Generic exception caught {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);

    }
}

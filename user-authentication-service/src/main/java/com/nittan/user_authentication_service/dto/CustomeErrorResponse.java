package com.nittan.user_authentication_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * DTO class to represent a custom error response.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomeErrorResponse {
    private HttpStatus httpStatus;  // HTTP status code associated with the error
    private String errorMessage;    // Error message describing the issue
    private String errorCode;       // Error code for categorizing the error
}

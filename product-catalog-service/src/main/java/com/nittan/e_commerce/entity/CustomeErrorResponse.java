package com.nittan.e_commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Represents a custom error response.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomeErrorResponse {
    private HttpStatus httpStatus;
    private String errorMessage;
    private String errorCode;
}

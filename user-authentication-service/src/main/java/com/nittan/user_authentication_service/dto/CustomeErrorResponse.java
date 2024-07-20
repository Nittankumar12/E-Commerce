package com.nittan.user_authentication_service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



// custom error response
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomeErrorResponse {
    private HttpStatus httpStatus;
    private String errorMessage;
    private String errorCode;
}

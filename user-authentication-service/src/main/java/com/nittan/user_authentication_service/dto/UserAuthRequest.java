package com.nittan.user_authentication_service.dto;

import lombok.Data;

// authentication request dto
@Data
public class UserAuthRequest {
    private String name;
    private String password;
}

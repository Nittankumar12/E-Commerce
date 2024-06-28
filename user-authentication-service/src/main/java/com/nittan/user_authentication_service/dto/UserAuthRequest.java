package com.nittan.user_authentication_service.dto;

import lombok.Data;

@Data
public class UserAuthRequest {
    private String name;
    private String password;
}

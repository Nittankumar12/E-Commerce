package com.nittan.user_authentication_service.dto;

import lombok.Data;

/**
 * DTO class representing a user authentication request.
 */
@Data
public class UserAuthRequest {
     String name;     // Username or identifier for authentication
     String password; // Password associated with the username
}

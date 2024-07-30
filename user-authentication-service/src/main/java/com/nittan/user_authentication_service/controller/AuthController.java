package com.nittan.user_authentication_service.controller;

import com.nittan.user_authentication_service.dto.UserAuthRequest;
import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * Endpoint to register a new user.
     * @param user The UserCredential object containing user details
     * @return A message indicating the result of the registration process
     */
    @PostMapping("/register")
    public String addUser(@RequestBody UserCredential user){

        return authService.saveUser(user);
    }

    /**
     * Endpoint to obtain a JWT token based on user credentials.
     * @param user The UserAuthRequest object containing user authentication details
     * @return A JWT token as a String
     */
    @PostMapping("/getToken")
    public String getToken(@RequestBody UserAuthRequest user){
        return authService.generateToken(user);
    }

    /**
     * Endpoint to validate a JWT token.
     * @param token The JWT token to be validated
     * @return A message indicating the validation result ("Token is Valid" if valid)
     */
    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is Valid";
    }


    /**
     * Endpoint get a user by email.
     * @param email of user
     * @return UserResponseDto object
     */
    @GetMapping("/getUserByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email){
        return authService.getUserByEmail(email);
    }


    @GetMapping("/getUserById")
    public ResponseEntity<?> getUserById(@RequestParam("id") Integer id){
        System.out.println("in controller");
        return authService.getUserById(id);
    }
}

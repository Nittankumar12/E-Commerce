package com.nittan.user_authentication_service.controller;

import com.nittan.user_authentication_service.dto.UserAuthRequest;
import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    // register new user
    @PostMapping("/register")
    public String addUser(@RequestBody UserCredential user){
        return authService.saveUser(user);
    }

    // get token through credentials
    @PostMapping("/getToken")
    public String getToken(@RequestBody UserAuthRequest user){
        return authService.generateToken(user);
    }

    // validating token
    @GetMapping("/validateToken")
    public String getToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is Valid";
    }



}

package com.nittan.user_authentication_service.service;

import com.nittan.user_authentication_service.dto.UserAuthRequest;
import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.exception.GenericException;
import com.nittan.user_authentication_service.exception.InvalidTokenException;
import com.nittan.user_authentication_service.exception.InvalidUserException;
import com.nittan.user_authentication_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user authentication operations.
 */
@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     * @param user The UserCredential object containing user details
     * @return A message indicating the result of the registration process
     */
    public String saveUser(UserCredential user){
        try {
            // Encode user's password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        } catch (Exception e) {
            throw new GenericException("Error while saving user to the database");
        }
        return "User registered successfully";
    }

    /**
     * Generate a JWT token based on user credentials.
     * @param user The UserAuthRequest object containing user authentication details
     * @return The generated JWT token
     */
    public String generateToken(UserAuthRequest user){
        try {
            // Authenticate user credentials
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            // Generate and return JWT token
            return jwtService.generateToken(user.getName());
        } catch (Exception e) {
            throw new InvalidUserException("Invalid credentials");
        }
    }

    /**
     * Validate a JWT token.
     * @param token The JWT token to validate
     */
    public void validateToken(String token){
        try {
            // Validate JWT token
            jwtService.validateToken(token);
        } catch (Exception e) {
            throw new InvalidTokenException("The token is expired or invalid");
        }
    }
}

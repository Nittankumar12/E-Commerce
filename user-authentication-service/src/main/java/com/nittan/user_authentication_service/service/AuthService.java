package com.nittan.user_authentication_service.service;

import com.nittan.user_authentication_service.dto.UserAuthRequest;
import com.nittan.user_authentication_service.dto.UserResponseDto;
import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.exception.GenericException;
import com.nittan.user_authentication_service.exception.InvalidTokenException;
import com.nittan.user_authentication_service.exception.InvalidUserException;
import com.nittan.user_authentication_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
            logger.info("user saved to the database");
        } catch (Exception e) {
            logger.error("error while saving to database");
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
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            // Generate and return JWT token
            logger.info("user authentication done & generating token");
            return jwtService.generateToken(user.getName());
        } catch (Exception e) {
            logger.error("Invalid user credentials");
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
            logger.info("token is valid");
        } catch (Exception e) {
            logger.error("invalid token or expired token");
            throw new InvalidTokenException("The token is expired or invalid");
        }
    }

    /**
     * get user by email
     * @param email of the user
     */
    public ResponseEntity<UserResponseDto>  getUserByEmail(String email) {
        UserCredential userCredential;
        try{
            userCredential = repository.findByEmail(email);
        }
        catch (Exception e){
            logger.error("User not found");
            throw new UsernameNotFoundException("User not found with this email");
        }
        UserResponseDto userResponseDto = new UserResponseDto(userCredential.getId(),userCredential.getName(),userCredential.getEmail());
        logger.error("returning user");
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }


    /**
     * get user by id
     * @param id of the user
     */
    public ResponseEntity<UserResponseDto> getUserById(int id) {
        Optional<UserCredential> userCredential;
        try{
            userCredential = repository.findById(id);
        }
        catch (Exception e){
            logger.error("user not found");
            throw new UsernameNotFoundException("User not found with this id");
        }
        if(userCredential.isEmpty()) throw new UsernameNotFoundException("user not found with this id");
        UserResponseDto userResponseDto = new UserResponseDto(userCredential.get().getId(),userCredential.get().getName(),userCredential.get().getEmail());
        logger.info("returning user");
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

}

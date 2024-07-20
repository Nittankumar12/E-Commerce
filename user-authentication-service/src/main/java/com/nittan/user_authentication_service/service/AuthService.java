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

    public String saveUser(UserCredential user){
        try{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        catch(Exception e){
            throw new GenericException("Password Encoding error");
        }
        try{
        repository.save(user);
        }catch(Exception e){
            throw new GenericException("Error while saving user to the database");
        }
        return "user registered successsfully";
    }


    // generating token
    public String generateToken(UserAuthRequest user){
        try{
            // authenticate user credentials
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        }catch(Exception e){
         throw new InvalidUserException("Invalid Credentials");
        }
        try{
            // generate token and return it
            return jwtService.generateToken((user.getName()));
        }
        catch (Exception e){
            throw new GenericException("Error while creating token");
        }
    }

    // validating token
    public void validateToken(String token){
        System.out.println("user auth validate token called");
        try{
            // validating token
            jwtService.validateToken(token);
        }
        catch(Exception e){
            throw new InvalidTokenException("The token is expired or invalid");
        }
    }

}

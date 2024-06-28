package com.nittan.user_authentication_service.service;


import com.nittan.user_authentication_service.dto.UserAuthRequest;
import com.nittan.user_authentication_service.entity.UserCredential;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return "user registered successsfully";
    }
    public String generateToken(UserAuthRequest user){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(user.getName());
        }
         throw new RuntimeException("not a valid user");
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}

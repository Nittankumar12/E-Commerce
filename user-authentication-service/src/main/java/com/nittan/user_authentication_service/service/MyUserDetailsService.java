package com.nittan.user_authentication_service.service;

import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.entity.UserPrincipal;
import com.nittan.user_authentication_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom implementation of UserDetailsService to load user details by username.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loading username");
        // Retrieve user credentials from repository by username
        Optional<UserCredential> userCredential = userRepository.findByName(username);
        System.out.println("got user");
        // Map user credentials to UserPrincipal or throw exception if user not found
        return userCredential.map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

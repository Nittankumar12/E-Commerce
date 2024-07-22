package com.nittan.user_authentication_service.service;

import com.nittan.user_authentication_service.entity.UserCredential;
import com.nittan.user_authentication_service.entity.UserPrincipal;
import com.nittan.user_authentication_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loading username");
        // Retrieve user credentials from repository by username
        Optional<UserCredential> userCredential = userRepository.findByName(username);

        // Map user credentials to UserPrincipal or throw exception if user not found
        return userCredential.map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

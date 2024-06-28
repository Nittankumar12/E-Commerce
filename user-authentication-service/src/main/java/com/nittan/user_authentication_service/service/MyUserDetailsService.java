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

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential =  userRepository.findByName(username);
        return userCredential.map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}

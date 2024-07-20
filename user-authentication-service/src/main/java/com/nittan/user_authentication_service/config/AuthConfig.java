package com.nittan.user_authentication_service.config;

import com.nittan.user_authentication_service.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    /**
     * Provides the custom implementation of UserDetailsService.
     * @return An instance of MyUserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    /**
     * Configures and provides the DaoAuthenticationProvider.
     * Sets the UserDetailsService and PasswordEncoder.
     * @return An instance of DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Provides the AuthenticationManager by retrieving it from the AuthenticationConfiguration.
     * @param authConfig The AuthenticationConfiguration instance
     * @return The AuthenticationManager instance
     * @throws Exception If there is an issue retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configures the HttpSecurity to define which endpoints require authentication and which do not.
     * Disables CSRF protection and allows specific endpoints to be accessed without authentication.
     * @param http The HttpSecurity instance to configure
     * @return The SecurityFilterChain instance
     * @throws Exception If there is an issue configuring HttpSecurity
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/register","/auth/getToken", "/auth/validateToken")
                        .permitAll()
                        .anyRequest().authenticated())
                .build();
    }

    /**
     * Provides the BCryptPasswordEncoder for encoding passwords.
     * @return The PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

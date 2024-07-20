package com.nittan.user_authentication_service.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Represents the principal (authenticated user) used by Spring Security.
 * Implements UserDetails interface to provide user authentication details.
 */
public class UserPrincipal implements UserDetails {
    private String username;  // Username used for authentication
    private String password;  // Password associated with the username

    public UserPrincipal(UserCredential userCredential){
        this.username = userCredential.getName();
        this.password = userCredential.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  // Not implemented, returns no authorities
    }

    @Override
    public String getPassword() {
        return password;  // Returns the user's password
    }

    @Override
    public String getUsername() {
        return username;  // Returns the username used for authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // User account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // User account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // User's credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true;  // User account is always enabled
    }
}

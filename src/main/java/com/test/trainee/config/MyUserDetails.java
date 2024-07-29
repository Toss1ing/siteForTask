package com.test.trainee.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.trainee.model.User;

// Custom UserDetails implementation for MyUserDetails.
public class MyUserDetails implements UserDetails {

    private User user; // User entity associated with this UserDetails.

    // Constructor to initialize with a User object.
    public MyUserDetails(final User user) {
        this.user = user;
    }

    // Method to retrieve the authorities (roles) granted to the user.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map user roles to Spring Security GrantedAuthority objects.
        return user.getUserRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // Method to retrieve the user's password.
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Method to retrieve the user's username.
    @Override
    public String getUsername() {
        return user.getName();
    }

    // Method to check if the user's account is not expired.
    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming accounts do not expire in this implementation.
    }

    // Method to check if the user's account is not locked.
    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming accounts are not locked in this implementation.
    }

    // Method to check if the user's credentials (password) are not expired.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming credentials do not expire in this implementation.
    }

    // Method to check if the user is enabled and active.
    @Override
    public boolean isEnabled() {
        return true; // Assuming all users are enabled and active.
    }

}

package com.test.trainee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.trainee.config.MyUserDetails;
import com.test.trainee.model.User;
import com.test.trainee.repository.UserRepository;

// Service class for loading user-specific data.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Injecting the UserRepository dependency.
    @Autowired
    private UserRepository userRepository;

    // Overriding the method to load user by username.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Finding the user by username.
        Optional<User> user = userRepository.findByName(username);

        // If user is found, map it to MyUserDetails. Otherwise, throw an exception.
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

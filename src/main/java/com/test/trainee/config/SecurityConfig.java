package com.test.trainee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.test.trainee.service.CustomUserDetailsService;

// Configuration class for Spring Security.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injecting CustomUserDetailsService dependency.
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Bean for password encoding using BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for authentication provider.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Bean for security filter chain configuration.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // Disable CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                // Configure authorization for HTTP requests.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/user/**").hasAuthority("ROLE_USER") // Allow all requests to user
                                                                                     // endpoints.
                        .requestMatchers("api/v1/user/new").permitAll() // Allow all requests to new user endpoint.
                        .requestMatchers("api/v1/both/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("api/v1/admin/**").hasAuthority("ROLE_ADMIN")) // Allow all requests to admin
                                                                                        // endpoints.
                // Enable form login and allow all users.
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();
    }
}

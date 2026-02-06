package com.example.student_management_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.student_management_system.service.CustomUserDetailsService;

@Configuration // This annotation indicates that this class is a configuration class for Spring. It allows Spring to recognize it as a source of bean definitions and other configuration settings.
@EnableWebSecurity // This annotation enables Spring Security's web security support and provides the Spring MVC integration. It allows you to configure web-based security for specific HTTP requests.
@EnableMethodSecurity // This annotation enables method-level security, allowing the use of @PreAuthorize, @PostAuthorize, @Secured, etc.
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Injecting the CustomUserDetailsService to be used for authentication. This service will be responsible for loading user-specific data during the authentication process.

    @Bean // This annotation indicates that the method will return a bean that should be managed by the Spring container. In this case, it will return a PasswordEncoder bean.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery) protection is disabled here, which is common for stateless APIs that use token-based authentication. However, be cautious when disabling CSRF protection in web applications that have a user interface.
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Allowing unauthenticated access to the authentication endpoints (e.g., login, register).
                .requestMatchers("/api/students/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // Requiring authentication for any other requests.

                )
                .httpBasic(httpBasic -> httpBasic.realmName("Student Management System")); // Enabling HTTP Basic authentication, which is a simple authentication scheme built into the HTTP protocol. It allows clients to provide a username and password when making requests.
        return http.build();
    }

    @Bean // This annotation indicates that the method will return a bean that should be managed by the Spring container. In this case, it will return a DaoAuthenticationProvider bean.
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

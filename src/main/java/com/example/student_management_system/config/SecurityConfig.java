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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // This method configures the security filter chain for HTTP requests. It defines how requests should be secured and what authentication mechanisms to use.
        http
                .csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery) protection is disabled here, which is common for stateless APIs that use token-based authentication. However, be cautious when disabling CSRF protection in web applications that have a user interface.
                .authorizeHttpRequests(auth -> auth // This line starts the configuration of authorization rules for HTTP requests. It allows you to specify which endpoints require authentication and what roles are needed to access them.
                .requestMatchers("/api/auth/**").permitAll() // Allowing unauthenticated access to the authentication endpoints (e.g., login, register).
                .requestMatchers("/api/students/**").hasAnyRole("ADMIN", "USER") // Restricting access to student-related endpoints to users with either "ADMIN" or "USER" roles.
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Restricting access to admin-related endpoints to users with the "ADMIN" role.
                .anyRequest().authenticated() // Requiring authentication for any other requests.

                )
                .httpBasic(httpBasic -> httpBasic.realmName("Student Management System")); // Enabling HTTP Basic authentication, which is a simple authentication scheme built into the HTTP protocol. It allows clients to provide a username and password when making requests.
        return http.build();
    }

    @Bean // This annotation indicates that the method will return a bean that should be managed by the Spring container. In this case, it will return a DaoAuthenticationProvider bean.
    public DaoAuthenticationProvider authenticationProvider() { // This method configures a DaoAuthenticationProvider, which is an implementation of AuthenticationProvider that retrieves user details from a UserDetailsService and uses a PasswordEncoder to validate passwords.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService); // This line creates a new instance of DaoAuthenticationProvider and sets the CustomUserDetailsService as the service to retrieve user details during authentication.
        authProvider.setPasswordEncoder(passwordEncoder()); // This line sets the PasswordEncoder to be used by the authentication provider. It ensures that the passwords provided during authentication are encoded and compared with the encoded passwords stored in the database.
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

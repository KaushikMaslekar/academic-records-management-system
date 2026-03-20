package com.example.student_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication Response DTO Contains JWT token and user information after
 * successful authentication
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    private String username;
    private String role;
    private String message;

    /**
     * Create a successful authentication response
     *
     * @param token JWT token
     * @param username Authenticated username
     * @param role User role
     * @param expiresIn Token expiration time in milliseconds
     * @return AuthenticationResponse object
     */
    public static AuthenticationResponse success(String token, String username, String role, long expiresIn) {
        return AuthenticationResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn / 1000) // Convert to seconds
                .username(username)
                .role(role)
                .message("Authentication successful")
                .build();
    }

    /**
     * Create an error response
     *
     * @param message Error message
     * @return AuthenticationResponse object
     */
    public static AuthenticationResponse error(String message) {
        return AuthenticationResponse.builder()
                .message(message)
                .build();
    }
}

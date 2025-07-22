package com.example.muscul_ia.dto;

/**
 * Login request DTO for authentication.
 * DTO de requête de connexion pour l'authentification.
 */
public class LoginRequest {
    private String username;
    private String password;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 
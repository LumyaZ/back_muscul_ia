package com.example.muscul_ia.dto;

/**
 * Login request DTO for authentication.
 * DTO de requête de connexion pour l'authentification.
 */
public class LoginRequest {
    private String email;
    private String password;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 
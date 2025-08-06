package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for user login requests.
 * Objet de transfert de données pour les requêtes de connexion utilisateur.
 */
public class LoginRequest {
    
    private String email;
    
    private String password;

    // Constructors
    public LoginRequest() {}
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
} 
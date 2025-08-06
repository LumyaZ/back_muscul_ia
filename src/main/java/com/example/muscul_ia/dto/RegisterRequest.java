package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for user registration requests.
 * Objet de transfert de données pour les requêtes d'enregistrement utilisateur.
 */
public class RegisterRequest {
    
    private String email;
    
    private String password;
        
    private String confirmPassword;

    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
    
    public String getConfirmPassword() { 
        return confirmPassword; 
    }
    
    public void setConfirmPassword(String confirmPassword) { 
        this.confirmPassword = confirmPassword; 
    }
} 
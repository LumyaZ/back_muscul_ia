package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for user login requests.
 * Objet de transfert de données pour les requêtes de connexion utilisateur.
 * 
 * This DTO is used to transfer login credentials from the client to the server
 * during the authentication process. It contains the email and password
 * required for user authentication.
 * 
 * Ce DTO est utilisé pour transférer les identifiants de connexion du client
 * vers le serveur pendant le processus d'authentification. Il contient l'email
 * et le mot de passe requis pour l'authentification utilisateur.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class LoginRequest {
    
    /**
     * User's email address used for authentication.
     * Adresse email de l'utilisateur utilisée pour l'authentification.
     */
    private String email;
    
    /**
     * User's password for authentication.
     * Mot de passe de l'utilisateur pour l'authentification.
     */
    private String password;

    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public LoginRequest() {}
    
    /**
     * Constructor with email and password.
     * Constructeur avec email et mot de passe.
     * 
     * @param email - User's email address
     * @param password - User's password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Get the user's email address.
     * Récupérer l'adresse email de l'utilisateur.
     * 
     * @return String - User's email address
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Set the user's email address.
     * Définir l'adresse email de l'utilisateur.
     * 
     * @param email - User's email address
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * Get the user's password.
     * Récupérer le mot de passe de l'utilisateur.
     * 
     * @return String - User's password
     */
    public String getPassword() { 
        return password; 
    }
    
    /**
     * Set the user's password.
     * Définir le mot de passe de l'utilisateur.
     * 
     * @param password - User's password
     */
    public void setPassword(String password) { 
        this.password = password; 
    }
} 
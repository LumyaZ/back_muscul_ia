package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for user registration requests.
 * Objet de transfert de données pour les requêtes d'enregistrement utilisateur.
 * 
 * This DTO is used to transfer registration data from the client to the server
 * during the user registration process. It contains the email, password, and
 * password confirmation required for creating a new user account.
 * 
 * Ce DTO est utilisé pour transférer les données d'enregistrement du client
 * vers le serveur pendant le processus d'enregistrement utilisateur. Il contient
 * l'email, le mot de passe et la confirmation du mot de passe requis pour
 * créer un nouveau compte utilisateur.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class RegisterRequest {
    
    /**
     * User's email address for the new account.
     * Adresse email de l'utilisateur pour le nouveau compte.
     */
    private String email;
    
    /**
     * User's password for the new account.
     * Mot de passe de l'utilisateur pour le nouveau compte.
     */
    private String password;
    
    /**
     * Password confirmation to ensure password accuracy.
     * Confirmation du mot de passe pour assurer l'exactitude du mot de passe.
     */
    private String confirmPassword;

    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public RegisterRequest() {}
    
    /**
     * Constructor with email, password, and confirmation.
     * Constructeur avec email, mot de passe et confirmation.
     * 
     * @param email - User's email address
     * @param password - User's password
     * @param confirmPassword - Password confirmation
     */
    public RegisterRequest(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
    
    /**
     * Get the password confirmation.
     * Récupérer la confirmation du mot de passe.
     * 
     * @return String - Password confirmation
     */
    public String getConfirmPassword() { 
        return confirmPassword; 
    }
    
    /**
     * Set the password confirmation.
     * Définir la confirmation du mot de passe.
     * 
     * @param confirmPassword - Password confirmation
     */
    public void setConfirmPassword(String confirmPassword) { 
        this.confirmPassword = confirmPassword; 
    }
} 
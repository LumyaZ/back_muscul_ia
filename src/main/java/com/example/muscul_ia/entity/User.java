package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a user in the authentication system.
 * Entité représentant un utilisateur dans le système d'authentification.
 * 
 * This entity stores basic user authentication information including email,
 * hashed password, and creation date. It maintains a one-to-one relationship
 * with UserProfile for additional user information.
 * 
 * Cette entité stocke les informations d'authentification de base de l'utilisateur
 * incluant l'email, le mot de passe hashé et la date de création. Elle maintient
 * une relation un-à-un avec UserProfile pour les informations utilisateur supplémentaires.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
public class User {
    
    /**
     * Unique identifier for the user.
     * Identifiant unique de l'utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email address used for login (must be unique).
     * Adresse email utilisée pour la connexion (doit être unique).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hashed password for security (never stored in plain text).
     * Mot de passe hashé pour la sécurité (jamais stocké en clair).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Timestamp when the user account was created.
     * Horodatage de la création du compte utilisateur.
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    /**
     * One-to-one relationship with UserProfile entity.
     * Relation un-à-un avec l'entité UserProfile.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    /**
     * Default constructor. Initializes creation date.
     * Constructeur par défaut. Initialise la date de création.
     */
    public User() {
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Constructor with email and password.
     * Constructeur avec email et mot de passe.
     * 
     * @param email - User email address
     * @param password - User password (will be hashed)
     */
    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    /**
     * Get the user ID.
     * Récupérer l'ID de l'utilisateur.
     * 
     * @return Long - User ID
     */
    public Long getId() { 
        return id; 
    }
    
    /**
     * Set the user ID.
     * Définir l'ID de l'utilisateur.
     * 
     * @param id - User ID
     */
    public void setId(Long id) { 
        this.id = id; 
    }
    
    /**
     * Get the user email address.
     * Récupérer l'adresse email de l'utilisateur.
     * 
     * @return String - User email
     */
    public String getEmail() { 
        return email; 
    }
    
    /**
     * Set the user email address.
     * Définir l'adresse email de l'utilisateur.
     * 
     * @param email - User email
     */
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    /**
     * Get the hashed password.
     * Récupérer le mot de passe hashé.
     * 
     * @return String - Hashed password
     */
    public String getPassword() { 
        return password; 
    }
    
    /**
     * Set the hashed password.
     * Définir le mot de passe hashé.
     * 
     * @param password - Hashed password
     */
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    /**
     * Get the account creation date.
     * Récupérer la date de création du compte.
     * 
     * @return LocalDateTime - Creation date
     */
    public LocalDateTime getCreationDate() { 
        return creationDate; 
    }
    
    /**
     * Set the account creation date.
     * Définir la date de création du compte.
     * 
     * @param creationDate - Creation date
     */
    public void setCreationDate(LocalDateTime creationDate) { 
        this.creationDate = creationDate; 
    }
    
    /**
     * Get the associated user profile.
     * Récupérer le profil utilisateur associé.
     * 
     * @return UserProfile - Associated user profile
     */
    public UserProfile getUserProfile() { 
        return userProfile; 
    }
    
    /**
     * Set the associated user profile.
     * Définir le profil utilisateur associé.
     * 
     * @param userProfile - Associated user profile
     */
    public void setUserProfile(UserProfile userProfile) { 
        this.userProfile = userProfile; 
    }
} 
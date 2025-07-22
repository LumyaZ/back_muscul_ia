package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * User entity for authentication.
 * Entité utilisateur pour l'authentification.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email for login (unique).
     * Email pour la connexion (unique).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hashed password (never store plain text).
     * Mot de passe hashé (ne jamais stocker en clair).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Account creation date.
     * Date de création du compte.
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    /**
     * One-to-one relationship with UserProfile.
     * Relation un-à-un avec UserProfile.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public UserProfile getUserProfile() { return userProfile; }
    public void setUserProfile(UserProfile userProfile) { this.userProfile = userProfile; }
} 
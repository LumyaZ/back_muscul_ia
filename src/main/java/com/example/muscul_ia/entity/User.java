package com.example.muscul_ia.entity;

import jakarta.persistence.*;

/**
 * User entity for authentication and authorization.
 * Entité utilisateur pour l'authentification et l'autorisation.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username for login (could be email).
     * Nom d'utilisateur pour la connexion (peut être un email).
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Hashed password (never store plain text).
     * Mot de passe hashé (ne jamais stocker en clair).
     */
    @Column(nullable = false)
    private String password;

    /**
     * User role (e.g., USER, ADMIN).
     * Rôle de l'utilisateur (ex : USER, ADMIN).
     */
    private String role;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 
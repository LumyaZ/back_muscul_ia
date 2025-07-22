package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for User (API exchange).
 * Objet de transfert de données pour l'utilisateur (échange API).
 */
public class UserDto {
    private Long id;
    private String username;
    private String role;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 
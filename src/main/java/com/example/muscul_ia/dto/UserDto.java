package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.User;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for User (API exchange).
 * Objet de transfert de données pour l'utilisateur (échange API).
 */
public class UserDto {
    private Long id;
    private String email;
    private LocalDateTime creationDate;

    // Constructors
    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.creationDate = user.getCreationDate();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
} 
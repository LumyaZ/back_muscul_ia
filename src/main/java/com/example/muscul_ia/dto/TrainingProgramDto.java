package com.example.muscul_ia.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for TrainingProgram entity.
 * Objet de transfert de données pour l'entité TrainingProgram.
 */
public class TrainingProgramDto {
    
    private Long id;
    private String name;
    
    private String description;
    private String difficultyLevel;
    
    private String category;
    private String targetAudience;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Long createdByUserId;
    

    public TrainingProgramDto() {}
    
    // Constructors
    public TrainingProgramDto(Long id, String name, String description, String difficultyLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
    }
    
    // Getters et Setters

    public Long getId() {
        return id;
    }
    

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }
    

    public String getDescription() {
        return description;
    }
    

    public void setDescription(String description) {
        this.description = description;
    }
    

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getTargetAudience() {
        return targetAudience;
    }
    
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getCreatedByUserId() {
        return createdByUserId;
    }
    
    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
} 
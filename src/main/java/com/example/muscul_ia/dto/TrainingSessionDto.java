package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.TrainingSession;
import java.time.LocalDateTime;

/**
 * DTO for training session responses.
 * DTO pour les réponses de session d'entraînement.
 */
public class TrainingSessionDto {
    
    private Long id;
    
    private Long userId;
    
    private String name;
    
    private String description;
    
    private LocalDateTime sessionDate;
    
    private Integer durationMinutes;
    
    private String sessionType;
    
    private Long trainingProgramId;
    
    private String trainingProgramName;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    // Constructors
    public TrainingSessionDto() {}
    
    public TrainingSessionDto(TrainingSession trainingSession) {
        this.id = trainingSession.getId();
        this.userId = trainingSession.getUser().getId();
        this.name = trainingSession.getName();
        this.description = trainingSession.getDescription();
        this.sessionDate = trainingSession.getSessionDate();
        this.durationMinutes = trainingSession.getDurationMinutes();
        this.sessionType = trainingSession.getSessionType();
        this.createdAt = trainingSession.getCreatedAt();
        this.updatedAt = trainingSession.getUpdatedAt();
        
        // Set training program information if available
        if (trainingSession.getTrainingProgram() != null) {
            this.trainingProgramId = trainingSession.getTrainingProgram().getId();
            this.trainingProgramName = trainingSession.getTrainingProgram().getName();
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
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
    
    public LocalDateTime getSessionDate() {
        return sessionDate;
    }
    
    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public String getSessionType() {
        return sessionType;
    }
    
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    
    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }
    
    public String getTrainingProgramName() {
        return trainingProgramName;
    }
    
    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
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
    
    @Override
    public String toString() {
        return "TrainingSessionDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sessionDate=" + sessionDate +
                ", durationMinutes=" + durationMinutes +
                ", sessionType='" + sessionType + '\'' +
                ", trainingProgramId=" + trainingProgramId +
                ", trainingProgramName='" + trainingProgramName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 
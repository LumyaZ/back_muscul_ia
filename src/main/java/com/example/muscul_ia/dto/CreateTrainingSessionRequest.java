package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for creating a training session.
 * DTO pour créer une session d'entraînement.
 */
public class CreateTrainingSessionRequest {
    
    @NotBlank(message = "Le nom de la session est obligatoire")
    @Size(min = 1, max = 100, message = "Le nom doit contenir entre 1 et 100 caractères")
    private String name;
    
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
    
    @NotNull(message = "La date de session est obligatoire")
    private LocalDateTime sessionDate;
    
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être d'au moins 1 minute")
    @Max(value = 480, message = "La durée ne peut pas dépasser 8 heures (480 minutes)")
    private Integer durationMinutes;
    
    @Size(max = 50, message = "Le type de session ne peut pas dépasser 50 caractères")
    private String sessionType;
    
    private Long trainingProgramId;
    
    // Constructors
    public CreateTrainingSessionRequest() {}
    
    public CreateTrainingSessionRequest(String name, String description, LocalDateTime sessionDate, 
                                     Integer durationMinutes, String sessionType, Long trainingProgramId) {
        this.name = name;
        this.description = description;
        this.sessionDate = sessionDate;
        this.durationMinutes = durationMinutes;
        this.sessionType = sessionType;
        this.trainingProgramId = trainingProgramId;
    }
    
    // Getters and Setters
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
    
    @Override
    public String toString() {
        return "CreateTrainingSessionRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sessionDate=" + sessionDate +
                ", durationMinutes=" + durationMinutes +
                ", sessionType='" + sessionType + '\'' +
                ", trainingProgramId=" + trainingProgramId +
                '}';
    }
} 
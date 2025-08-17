package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DTO for creating a training session.
 */
public class CreateTrainingSessionRequest {
    
    @NotBlank(message = "Session name is required")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
    private String name;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    @NotBlank(message = "Session date is required")
    private String sessionDate; // Accepte les dates en format String
    
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 480, message = "Duration cannot exceed 8 hours (480 minutes)")
    private Integer durationMinutes;
    
    @Size(max = 50, message = "Session type cannot exceed 50 characters")
    private String sessionType;
    
    private String trainingProgramId; // Accepte les IDs en format String
    
    private String userId; // Accepte les IDs en format String
    
    // Constructors
    public CreateTrainingSessionRequest() {}
    
    public CreateTrainingSessionRequest(String name, String description, String sessionDate, 
                                     Integer durationMinutes, String sessionType, String trainingProgramId, String userId) {
        this.name = name;
        this.description = description;
        this.sessionDate = sessionDate;
        this.durationMinutes = durationMinutes;
        this.sessionType = sessionType;
        this.trainingProgramId = trainingProgramId;
        this.userId = userId;
    }
    
    // Méthodes de conversion pour maintenir la compatibilité
    public LocalDateTime getSessionDateAsLocalDateTime() {
        try {
            return LocalDateTime.parse(this.sessionDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid session date format. Expected format: yyyy-MM-ddTHH:mm:ss");
        }
    }
    
    public Long getTrainingProgramIdAsLong() {
        try {
            return this.trainingProgramId != null ? Long.parseLong(this.trainingProgramId) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid training program ID format");
        }
    }
    
    public Long getUserIdAsLong() {
        try {
            return this.userId != null ? Long.parseLong(this.userId) : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID format");
        }
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
    
    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
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
    
    public String getTrainingProgramId() {
        return trainingProgramId;
    }
    
    public void setTrainingProgramId(String trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "CreateTrainingSessionRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sessionDate='" + sessionDate + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", sessionType='" + sessionType + '\'' +
                ", trainingProgramId='" + trainingProgramId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
} 
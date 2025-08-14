package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for creating a training session.
 */
public class CreateTrainingSessionRequest {
    
    @NotBlank(message = "Session name is required")
    @Size(min = 1, max = 100, message = "Name must contain between 1 and 100 characters")
    private String name;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Session date is required")
    private LocalDateTime sessionDate;
    
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 480, message = "Duration cannot exceed 8 hours (480 minutes)")
    private Integer durationMinutes;
    
    @Size(max = 50, message = "Session type cannot exceed 50 characters")
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
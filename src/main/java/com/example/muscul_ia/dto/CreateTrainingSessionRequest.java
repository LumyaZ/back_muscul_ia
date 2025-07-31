package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for creating a training session.
 * DTO pour créer une session d'entraînement.
 * 
 * This DTO contains all the necessary information to create a new training session
 * including the session name, description, date, duration, and optional training program.
 * 
 * Ce DTO contient toutes les informations nécessaires pour créer une nouvelle session
 * d'entraînement incluant le nom de la session, la description, la date, la durée
 * et le programme d'entraînement optionnel.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CreateTrainingSessionRequest {
    
    /**
     * Name of the training session.
     * Nom de la session d'entraînement.
     */
    @NotBlank(message = "Le nom de la session est obligatoire")
    @Size(min = 1, max = 100, message = "Le nom doit contenir entre 1 et 100 caractères")
    private String name;
    
    /**
     * Description or notes about the training session.
     * Description ou notes sur la session d'entraînement.
     */
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
    
    /**
     * Date when the training session was performed.
     * Date à laquelle la session d'entraînement a été effectuée.
     */
    @NotNull(message = "La date de session est obligatoire")
    private LocalDateTime sessionDate;
    
    /**
     * Duration of the training session in minutes.
     * Durée de la session d'entraînement en minutes.
     */
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être d'au moins 1 minute")
    @Max(value = 480, message = "La durée ne peut pas dépasser 8 heures (480 minutes)")
    private Integer durationMinutes;
    
    /**
     * Type of training session (e.g., "Musculation", "Cardio", "HIIT").
     * Type de session d'entraînement (ex: "Musculation", "Cardio", "HIIT").
     */
    @Size(max = 50, message = "Le type de session ne peut pas dépasser 50 caractères")
    private String sessionType;
    
    /**
     * ID of the training program associated with this session (optional).
     * ID du programme d'entraînement associé à cette session (optionnel).
     */
    private Long trainingProgramId;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public CreateTrainingSessionRequest() {}
    
    /**
     * Constructor with all required fields.
     * Constructeur avec tous les champs requis.
     * 
     * @param name - Session name
     * @param description - Session description
     * @param sessionDate - Session date
     * @param durationMinutes - Session duration in minutes
     * @param sessionType - Session type
     * @param trainingProgramId - Associated training program ID
     */
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
    /**
     * Get the session name.
     * Récupérer le nom de la session.
     * 
     * @return String - Session name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the session name.
     * Définir le nom de la session.
     * 
     * @param name - Session name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the session description.
     * Récupérer la description de la session.
     * 
     * @return String - Session description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the session description.
     * Définir la description de la session.
     * 
     * @param description - Session description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the session date.
     * Récupérer la date de la session.
     * 
     * @return LocalDateTime - Session date
     */
    public LocalDateTime getSessionDate() {
        return sessionDate;
    }
    
    /**
     * Set the session date.
     * Définir la date de la session.
     * 
     * @param sessionDate - Session date
     */
    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }
    
    /**
     * Get the session duration in minutes.
     * Récupérer la durée de la session en minutes.
     * 
     * @return Integer - Session duration in minutes
     */
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    /**
     * Set the session duration in minutes.
     * Définir la durée de la session en minutes.
     * 
     * @param durationMinutes - Session duration in minutes
     */
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    /**
     * Get the session type.
     * Récupérer le type de session.
     * 
     * @return String - Session type
     */
    public String getSessionType() {
        return sessionType;
    }
    
    /**
     * Set the session type.
     * Définir le type de session.
     * 
     * @param sessionType - Session type
     */
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    
    /**
     * Get the training program ID.
     * Récupérer l'ID du programme d'entraînement.
     * 
     * @return Long - Training program ID
     */
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    
    /**
     * Set the training program ID.
     * Définir l'ID du programme d'entraînement.
     * 
     * @param trainingProgramId - Training program ID
     */
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
package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.TrainingSession;
import java.time.LocalDateTime;

/**
 * DTO for training session responses.
 * DTO pour les réponses de session d'entraînement.
 * 
 * This DTO contains all the information about a training session that should
 * be returned to the client, including session details, user information,
 * and associated training program.
 * 
 * Ce DTO contient toutes les informations sur une session d'entraînement qui
 * doivent être retournées au client, incluant les détails de la session,
 * les informations utilisateur et le programme d'entraînement associé.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class TrainingSessionDto {
    
    /**
     * Unique identifier for the training session.
     * Identifiant unique de la session d'entraînement.
     */
    private Long id;
    
    /**
     * ID of the user who performed the session.
     * ID de l'utilisateur qui a effectué la session.
     */
    private Long userId;
    
    /**
     * Name of the training session.
     * Nom de la session d'entraînement.
     */
    private String name;
    
    /**
     * Description or notes about the training session.
     * Description ou notes sur la session d'entraînement.
     */
    private String description;
    
    /**
     * Date when the training session was performed.
     * Date à laquelle la session d'entraînement a été effectuée.
     */
    private LocalDateTime sessionDate;
    
    /**
     * Duration of the training session in minutes.
     * Durée de la session d'entraînement en minutes.
     */
    private Integer durationMinutes;
    
    /**
     * Type of training session (e.g., "Musculation", "Cardio", "HIIT").
     * Type de session d'entraînement (ex: "Musculation", "Cardio", "HIIT").
     */
    private String sessionType;
    
    /**
     * ID of the training program associated with this session (optional).
     * ID du programme d'entraînement associé à cette session (optionnel).
     */
    private Long trainingProgramId;
    
    /**
     * Name of the training program associated with this session (optional).
     * Nom du programme d'entraînement associé à cette session (optionnel).
     */
    private String trainingProgramName;
    
    /**
     * Timestamp when the session record was created.
     * Horodatage de création de l'enregistrement de session.
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the session record was last updated.
     * Horodatage de la dernière mise à jour de l'enregistrement de session.
     */
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public TrainingSessionDto() {}
    
    /**
     * Constructor from TrainingSession entity.
     * Constructeur à partir de l'entité TrainingSession.
     * 
     * @param trainingSession - TrainingSession entity
     */
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
    /**
     * Get the session ID.
     * Récupérer l'ID de la session.
     * 
     * @return Long - Session ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the session ID.
     * Définir l'ID de la session.
     * 
     * @param id - Session ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the user ID.
     * Récupérer l'ID de l'utilisateur.
     * 
     * @return Long - User ID
     */
    public Long getUserId() {
        return userId;
    }
    
    /**
     * Set the user ID.
     * Définir l'ID de l'utilisateur.
     * 
     * @param userId - User ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
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
    
    /**
     * Get the training program name.
     * Récupérer le nom du programme d'entraînement.
     * 
     * @return String - Training program name
     */
    public String getTrainingProgramName() {
        return trainingProgramName;
    }
    
    /**
     * Set the training program name.
     * Définir le nom du programme d'entraînement.
     * 
     * @param trainingProgramName - Training program name
     */
    public void setTrainingProgramName(String trainingProgramName) {
        this.trainingProgramName = trainingProgramName;
    }
    
    /**
     * Get the creation timestamp.
     * Récupérer l'horodatage de création.
     * 
     * @return LocalDateTime - Creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Set the creation timestamp.
     * Définir l'horodatage de création.
     * 
     * @param createdAt - Creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Get the last update timestamp.
     * Récupérer l'horodatage de dernière mise à jour.
     * 
     * @return LocalDateTime - Last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Set the last update timestamp.
     * Définir l'horodatage de dernière mise à jour.
     * 
     * @param updatedAt - Last update timestamp
     */
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
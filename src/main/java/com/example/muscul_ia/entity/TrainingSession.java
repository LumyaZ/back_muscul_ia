package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a training session record.
 * Entité représentant un enregistrement de session d'entraînement.
 * 
 * This entity stores information about completed training sessions including
 * the user who performed the session, the training program (if any), session
 * details, duration, and notes.
 * 
 * Cette entité stocke les informations sur les sessions d'entraînement terminées
 * incluant l'utilisateur qui a effectué la session, le programme d'entraînement
 * (si applicable), les détails de la session, la durée et les notes.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "training_sessions")
public class TrainingSession {
    
    /**
     * Unique identifier for the training session.
     * Identifiant unique de la session d'entraînement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User who performed the training session.
     * Utilisateur qui a effectué la session d'entraînement.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Training program associated with this session (optional).
     * Programme d'entraînement associé à cette session (optionnel).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id")
    private TrainingProgram trainingProgram;
    
    /**
     * Name of the training session.
     * Nom de la session d'entraînement.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * Description or notes about the training session.
     * Description ou notes sur la session d'entraînement.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Date when the training session was performed.
     * Date à laquelle la session d'entraînement a été effectuée.
     */
    @Column(name = "session_date", nullable = false)
    private LocalDateTime sessionDate;
    
    /**
     * Duration of the training session in minutes.
     * Durée de la session d'entraînement en minutes.
     */
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
    
    /**
     * Type of training session (e.g., "Musculation", "Cardio", "HIIT").
     * Type de session d'entraînement (ex: "Musculation", "Cardio", "HIIT").
     */
    @Column(name = "session_type", length = 50)
    private String sessionType;
    
    /**
     * Timestamp when the session record was created.
     * Horodatage de création de l'enregistrement de session.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the session record was last updated.
     * Horodatage de la dernière mise à jour de l'enregistrement de session.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public TrainingSession() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with user and session date.
     * Constructeur avec utilisateur et date de session.
     * 
     * @param user - User who performed the session
     * @param sessionDate - Date when the session was performed
     */
    public TrainingSession(User user, LocalDateTime sessionDate) {
        this();
        this.user = user;
        this.sessionDate = sessionDate;
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
     * Get the user who performed the session.
     * Récupérer l'utilisateur qui a effectué la session.
     * 
     * @return User - User who performed the session
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Set the user who performed the session.
     * Définir l'utilisateur qui a effectué la session.
     * 
     * @param user - User who performed the session
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Get the training program associated with this session.
     * Récupérer le programme d'entraînement associé à cette session.
     * 
     * @return TrainingProgram - Associated training program
     */
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }
    
    /**
     * Set the training program associated with this session.
     * Définir le programme d'entraînement associé à cette session.
     * 
     * @param trainingProgram - Associated training program
     */
    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
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
    
    /**
     * JPA lifecycle method called before entity update.
     * Sets the updatedAt timestamp to current time.
     * 
     * Méthode de cycle de vie JPA appelée avant la mise à jour de l'entité.
     * Définit l'horodatage updatedAt à l'heure actuelle.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 
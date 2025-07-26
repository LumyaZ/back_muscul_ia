package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a training program in the system.
 * Entité représentant un programme d'entraînement dans le système.
 * 
 * This entity stores information about training programs including their
 * name, description, difficulty level, duration, target audience, and
 * equipment requirements. It also tracks creation and update timestamps
 * and maintains relationships with the user who created the program.
 * 
 * Cette entité stocke les informations sur les programmes d'entraînement
 * incluant leur nom, description, niveau de difficulté, durée, audience
 * cible et équipement requis. Elle suit également les horodatages de
 * création et de mise à jour et maintient les relations avec l'utilisateur
 * qui a créé le programme.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "training_programs")
public class TrainingProgram {
    
    /**
     * Unique identifier for the training program.
     * Identifiant unique du programme d'entraînement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Name of the training program.
     * Nom du programme d'entraînement.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * Detailed description of the training program.
     * Description détaillée du programme d'entraînement.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Difficulty level of the program (e.g., "Débutant", "Intermédiaire", "Avancé").
     * Niveau de difficulté du programme (ex: "Débutant", "Intermédiaire", "Avancé").
     */
    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel;
    
    /**
     * Duration of the program in weeks.
     * Durée du programme en semaines.
     */
    @Column(name = "duration_weeks")
    private Integer durationWeeks;
    
    /**
     * Number of training sessions per week.
     * Nombre de sessions d'entraînement par semaine.
     */
    @Column(name = "sessions_per_week")
    private Integer sessionsPerWeek;
    
    /**
     * Estimated duration of each session in minutes.
     * Durée estimée de chaque session en minutes.
     */
    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;
    
    /**
     * Category of the training program (e.g., "Musculation", "Cardio", "Mixte").
     * Catégorie du programme d'entraînement (ex: "Musculation", "Cardio", "Mixte").
     */
    @Column(name = "category", length = 50)
    private String category;
    
    /**
     * Target audience for the program (e.g., "Débutants", "Sportifs confirmés").
     * Audience cible pour le programme (ex: "Débutants", "Sportifs confirmés").
     */
    @Column(name = "target_audience", length = 100)
    private String targetAudience;
    
    /**
     * Equipment required to follow the program.
     * Équipement requis pour suivre le programme.
     */
    @Column(name = "equipment_required", columnDefinition = "TEXT")
    private String equipmentRequired;
    
    /**
     * URL to an image representing the program.
     * URL vers une image représentant le programme.
     */
    @Column(name = "image_url")
    private String imageUrl;
    
    /**
     * Flag indicating if the program is public and visible to all users.
     * Indicateur indiquant si le programme est public et visible par tous les utilisateurs.
     */
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;
    
    /**
     * Flag indicating if the program is currently active and available for use.
     * Indicateur indiquant si le programme est actuellement actif et disponible.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Timestamp when the program was created.
     * Horodatage de la création du programme.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the program was last updated.
     * Horodatage de la dernière mise à jour du programme.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * User who created this training program.
     * Utilisateur qui a créé ce programme d'entraînement.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;
    
    /**
     * Default constructor. Initializes creation timestamp.
     * Constructeur par défaut. Initialise l'horodatage de création.
     */
    public TrainingProgram() {
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic program information.
     * Constructeur avec les informations de base du programme.
     * 
     * @param name - Program name
     * @param description - Program description
     * @param difficultyLevel - Program difficulty level
     */
    public TrainingProgram(String name, String description, String difficultyLevel) {
        this();
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
    }
    
    // Getters et Setters
    /**
     * Get the program ID.
     * Récupérer l'ID du programme.
     * 
     * @return Long - Program ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the program ID.
     * Définir l'ID du programme.
     * 
     * @param id - Program ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the program name.
     * Récupérer le nom du programme.
     * 
     * @return String - Program name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the program name.
     * Définir le nom du programme.
     * 
     * @param name - Program name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the program description.
     * Récupérer la description du programme.
     * 
     * @return String - Program description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the program description.
     * Définir la description du programme.
     * 
     * @param description - Program description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the difficulty level.
     * Récupérer le niveau de difficulté.
     * 
     * @return String - Difficulty level
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    
    /**
     * Set the difficulty level.
     * Définir le niveau de difficulté.
     * 
     * @param difficultyLevel - Difficulty level
     */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    /**
     * Get the duration in weeks.
     * Récupérer la durée en semaines.
     * 
     * @return Integer - Duration in weeks
     */
    public Integer getDurationWeeks() {
        return durationWeeks;
    }
    
    /**
     * Set the duration in weeks.
     * Définir la durée en semaines.
     * 
     * @param durationWeeks - Duration in weeks
     */
    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }
    
    /**
     * Get the number of sessions per week.
     * Récupérer le nombre de sessions par semaine.
     * 
     * @return Integer - Sessions per week
     */
    public Integer getSessionsPerWeek() {
        return sessionsPerWeek;
    }
    
    /**
     * Set the number of sessions per week.
     * Définir le nombre de sessions par semaine.
     * 
     * @param sessionsPerWeek - Sessions per week
     */
    public void setSessionsPerWeek(Integer sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }
    
    /**
     * Get the estimated duration in minutes.
     * Récupérer la durée estimée en minutes.
     * 
     * @return Integer - Estimated duration in minutes
     */
    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }
    
    /**
     * Set the estimated duration in minutes.
     * Définir la durée estimée en minutes.
     * 
     * @param estimatedDurationMinutes - Estimated duration in minutes
     */
    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }
    
    /**
     * Get the program category.
     * Récupérer la catégorie du programme.
     * 
     * @return String - Program category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Set the program category.
     * Définir la catégorie du programme.
     * 
     * @param category - Program category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Get the target audience.
     * Récupérer l'audience cible.
     * 
     * @return String - Target audience
     */
    public String getTargetAudience() {
        return targetAudience;
    }
    
    /**
     * Set the target audience.
     * Définir l'audience cible.
     * 
     * @param targetAudience - Target audience
     */
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }
    
    /**
     * Get the required equipment.
     * Récupérer l'équipement requis.
     * 
     * @return String - Required equipment
     */
    public String getEquipmentRequired() {
        return equipmentRequired;
    }
    
    /**
     * Set the required equipment.
     * Définir l'équipement requis.
     * 
     * @param equipmentRequired - Required equipment
     */
    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }
    
    /**
     * Get the image URL.
     * Récupérer l'URL de l'image.
     * 
     * @return String - Image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Set the image URL.
     * Définir l'URL de l'image.
     * 
     * @param imageUrl - Image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Check if the program is public.
     * Vérifier si le programme est public.
     * 
     * @return Boolean - True if public, false otherwise
     */
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    /**
     * Set the public status.
     * Définir le statut public.
     * 
     * @param isPublic - Public status
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    /**
     * Check if the program is active.
     * Vérifier si le programme est actif.
     * 
     * @return Boolean - True if active, false otherwise
     */
    public Boolean getIsActive() {
        return isActive;
    }
    
    /**
     * Set the active status.
     * Définir le statut actif.
     * 
     * @param isActive - Active status
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
     * Get the user who created this program.
     * Récupérer l'utilisateur qui a créé ce programme.
     * 
     * @return User - User who created the program
     */
    public User getCreatedByUser() {
        return createdByUser;
    }
    
    /**
     * Set the user who created this program.
     * Définir l'utilisateur qui a créé ce programme.
     * 
     * @param createdByUser - User who created the program
     */
    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
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
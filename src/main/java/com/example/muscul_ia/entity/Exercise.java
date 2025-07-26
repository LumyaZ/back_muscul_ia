package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an exercise in the training system.
 * Entité représentant un exercice dans le système d'entraînement.
 * 
 * This entity stores information about individual exercises including their
 * name, description, category, muscle group, equipment requirements, and
 * difficulty level. It also tracks creation and update timestamps for
 * audit purposes.
 * 
 * Cette entité stocke les informations sur les exercices individuels incluant
 * leur nom, description, catégorie, groupe musculaire, équipement requis et
 * niveau de difficulté. Elle suit également les horodatages de création et
 * de mise à jour pour des fins d'audit.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "exercises")
public class Exercise {
    
    /**
     * Unique identifier for the exercise.
     * Identifiant unique de l'exercice.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Name of the exercise.
     * Nom de l'exercice.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * Detailed description of how to perform the exercise.
     * Description détaillée de la façon d'effectuer l'exercice.
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    /**
     * Category of the exercise (e.g., "Musculation", "Cardio", "Flexibilité").
     * Catégorie de l'exercice (ex: "Musculation", "Cardio", "Flexibilité").
     */
    @Column(name = "category", nullable = false, length = 50)
    private String category;
    
    /**
     * Primary muscle group targeted by the exercise (e.g., "Pectoraux", "Dos", "Jambes").
     * Groupe musculaire principal ciblé par l'exercice (ex: "Pectoraux", "Dos", "Jambes").
     */
    @Column(name = "muscle_group", length = 100)
    private String muscleGroup;
    
    /**
     * Equipment required to perform the exercise (e.g., "Haltères", "Barre", "Poids du corps").
     * Équipement requis pour effectuer l'exercice (ex: "Haltères", "Barre", "Poids du corps").
     */
    @Column(name = "equipment_needed", length = 100)
    private String equipmentNeeded;
    
    /**
     * Difficulty level of the exercise (e.g., "Débutant", "Intermédiaire", "Avancé").
     * Niveau de difficulté de l'exercice (ex: "Débutant", "Intermédiaire", "Avancé").
     */
    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel;
    
    /**
     * URL to a video demonstrating the exercise.
     * URL vers une vidéo démontrant l'exercice.
     */
    @Column(name = "video_url")
    private String videoUrl;
    
    /**
     * URL to an image showing the exercise.
     * URL vers une image montrant l'exercice.
     */
    @Column(name = "image_url")
    private String imageUrl;
    
    /**
     * Flag indicating if the exercise is currently active and available for use.
     * Indicateur indiquant si l'exercice est actuellement actif et disponible.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    /**
     * Timestamp when the exercise was created.
     * Horodatage de la création de l'exercice.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the exercise was last updated.
     * Horodatage de la dernière mise à jour de l'exercice.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor. Initializes creation timestamp.
     * Constructeur par défaut. Initialise l'horodatage de création.
     */
    public Exercise() {
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Constructor with basic exercise information.
     * Constructeur avec les informations de base de l'exercice.
     * 
     * @param name - Exercise name
     * @param description - Exercise description
     * @param category - Exercise category
     */
    public Exercise(String name, String description, String category) {
        this();
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    // Getters et Setters
    /**
     * Get the exercise ID.
     * Récupérer l'ID de l'exercice.
     * 
     * @return Long - Exercise ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the exercise ID.
     * Définir l'ID de l'exercice.
     * 
     * @param id - Exercise ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the exercise name.
     * Récupérer le nom de l'exercice.
     * 
     * @return String - Exercise name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the exercise name.
     * Définir le nom de l'exercice.
     * 
     * @param name - Exercise name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the exercise description.
     * Récupérer la description de l'exercice.
     * 
     * @return String - Exercise description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the exercise description.
     * Définir la description de l'exercice.
     * 
     * @param description - Exercise description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the exercise category.
     * Récupérer la catégorie de l'exercice.
     * 
     * @return String - Exercise category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Set the exercise category.
     * Définir la catégorie de l'exercice.
     * 
     * @param category - Exercise category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Get the muscle group targeted by the exercise.
     * Récupérer le groupe musculaire ciblé par l'exercice.
     * 
     * @return String - Muscle group
     */
    public String getMuscleGroup() {
        return muscleGroup;
    }
    
    /**
     * Set the muscle group targeted by the exercise.
     * Définir le groupe musculaire ciblé par l'exercice.
     * 
     * @param muscleGroup - Muscle group
     */
    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
    
    /**
     * Get the equipment needed for the exercise.
     * Récupérer l'équipement nécessaire pour l'exercice.
     * 
     * @return String - Required equipment
     */
    public String getEquipmentNeeded() {
        return equipmentNeeded;
    }
    
    /**
     * Set the equipment needed for the exercise.
     * Définir l'équipement nécessaire pour l'exercice.
     * 
     * @param equipmentNeeded - Required equipment
     */
    public void setEquipmentNeeded(String equipmentNeeded) {
        this.equipmentNeeded = equipmentNeeded;
    }
    
    /**
     * Get the difficulty level of the exercise.
     * Récupérer le niveau de difficulté de l'exercice.
     * 
     * @return String - Difficulty level
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    
    /**
     * Set the difficulty level of the exercise.
     * Définir le niveau de difficulté de l'exercice.
     * 
     * @param difficultyLevel - Difficulty level
     */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    /**
     * Get the video URL for the exercise.
     * Récupérer l'URL de la vidéo pour l'exercice.
     * 
     * @return String - Video URL
     */
    public String getVideoUrl() {
        return videoUrl;
    }
    
    /**
     * Set the video URL for the exercise.
     * Définir l'URL de la vidéo pour l'exercice.
     * 
     * @param videoUrl - Video URL
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    /**
     * Get the image URL for the exercise.
     * Récupérer l'URL de l'image pour l'exercice.
     * 
     * @return String - Image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    /**
     * Set the image URL for the exercise.
     * Définir l'URL de l'image pour l'exercice.
     * 
     * @param imageUrl - Image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * Check if the exercise is active.
     * Vérifier si l'exercice est actif.
     * 
     * @return Boolean - True if active, false otherwise
     */
    public Boolean getIsActive() {
        return isActive;
    }
    
    /**
     * Set the active status of the exercise.
     * Définir le statut actif de l'exercice.
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
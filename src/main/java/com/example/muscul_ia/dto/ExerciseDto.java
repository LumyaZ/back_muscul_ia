package com.example.muscul_ia.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Exercise entity.
 * Objet de transfert de données pour l'entité Exercise.
 * 
 * This DTO is used to transfer exercise data between the controller layer
 * and the client. It contains all the essential exercise information
 * without exposing internal entity details or JPA annotations.
 * 
 * Ce DTO est utilisé pour transférer les données d'exercice entre la couche
 * contrôleur et le client. Il contient toutes les informations essentielles
 * de l'exercice sans exposer les détails internes de l'entité ou les annotations JPA.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class ExerciseDto {
    
    /**
     * Unique identifier for the exercise.
     * Identifiant unique de l'exercice.
     */
    private Long id;
    
    /**
     * Name of the exercise.
     * Nom de l'exercice.
     */
    private String name;
    
    /**
     * Detailed description of how to perform the exercise.
     * Description détaillée de la façon d'effectuer l'exercice.
     */
    private String description;
    
    /**
     * Category of the exercise (e.g., "Musculation", "Cardio", "Flexibilité").
     * Catégorie de l'exercice (ex: "Musculation", "Cardio", "Flexibilité").
     */
    private String category;
    
    /**
     * Primary muscle group targeted by the exercise.
     * Groupe musculaire principal ciblé par l'exercice.
     */
    private String muscleGroup;
    
    /**
     * Equipment required to perform the exercise.
     * Équipement requis pour effectuer l'exercice.
     */
    private String equipmentNeeded;
    
    /**
     * Difficulty level of the exercise.
     * Niveau de difficulté de l'exercice.
     */
    private String difficultyLevel;
    
    /**
     * Flag indicating if the exercise is currently active.
     * Indicateur indiquant si l'exercice est actuellement actif.
     */
    private Boolean isActive;
    
    /**
     * Timestamp when the exercise was created.
     * Horodatage de la création de l'exercice.
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the exercise was last updated.
     * Horodatage de la dernière mise à jour de l'exercice.
     */
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public ExerciseDto() {}
    
    /**
     * Constructor with basic exercise information.
     * Constructeur avec les informations de base de l'exercice.
     * 
     * @param id - Exercise ID
     * @param name - Exercise name
     * @param description - Exercise description
     * @param category - Exercise category
     */
    public ExerciseDto(Long id, String name, String description, String category) {
        this.id = id;
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
} 
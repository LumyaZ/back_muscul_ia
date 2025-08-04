package com.example.muscul_ia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for adding an exercise to a training program.
 * Objet de transfert de données pour ajouter un exercice à un programme d'entraînement.
 * 
 * This DTO contains all the necessary information to add an exercise
 * to an existing training program, including exercise parameters,
 * order, and optional settings.
 * 
 * Ce DTO contient toutes les informations nécessaires pour ajouter
 * un exercice à un programme d'entraînement existant, incluant les
 * paramètres d'exercice, l'ordre et les paramètres optionnels.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CreateProgramExerciseRequest {
    
    /**
     * ID of the exercise to add to the program.
     * ID de l'exercice à ajouter au programme.
     */
    @NotNull(message = "L'ID de l'exercice est requis")
    private Long exerciseId;
    
    /**
     * Number of sets for this exercise.
     * Nombre de séries pour cet exercice.
     */
    @NotNull(message = "Le nombre de séries est requis")
    @Min(value = 1, message = "Le nombre de séries doit être au moins 1")
    private Integer setsCount;
    
    /**
     * Number of repetitions per set (optional for time-based exercises).
     * Nombre de répétitions par série (optionnel pour les exercices basés sur le temps).
     */
    @Min(value = 1, message = "Le nombre de répétitions doit être au moins 1")
    private Integer repsCount;
    
    /**
     * Rest duration in seconds between sets.
     * Durée de repos en secondes entre les séries.
     */
    @NotNull(message = "La durée de repos est requise")
    @Min(value = 0, message = "La durée de repos ne peut pas être négative")
    private Integer restDurationSeconds;
    
    /**
     * Weight in kilograms (optional).
     * Poids en kilogrammes (optionnel).
     */
    @Min(value = 0, message = "Le poids ne peut pas être négatif")
    private Double weightKg;
    
    /**
     * Distance in meters (optional for cardio exercises).
     * Distance en mètres (optionnel pour les exercices cardio).
     */
    @Min(value = 0, message = "La distance ne peut pas être négative")
    private Double distanceMeters;
    
    /**
     * Additional notes about the exercise in the program.
     * Notes supplémentaires sur l'exercice dans le programme.
     */
    private String notes;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public CreateProgramExerciseRequest() {}

    /**
     * Constructor with all fields.
     * Constructeur avec tous les champs.
     * 
     * @param exerciseId - ID of the exercise
     * @param setsCount - Number of sets
     * @param repsCount - Number of repetitions
     * @param restDurationSeconds - Rest duration in seconds
     * @param weightKg - Weight in kilograms
     * @param distanceMeters - Distance in meters
     * @param notes - Additional notes
     */
    public CreateProgramExerciseRequest(Long exerciseId, Integer setsCount,
                                      Integer repsCount, Integer restDurationSeconds,
                                      Double weightKg, Double distanceMeters, String notes) {
        this.exerciseId = exerciseId;
        this.setsCount = setsCount;
        this.repsCount = repsCount;
        this.restDurationSeconds = restDurationSeconds;
        this.weightKg = weightKg;
        this.distanceMeters = distanceMeters;
        this.notes = notes;
    }

    // Getters and Setters

    /**
     * Gets the exercise ID.
     * Obtient l'ID de l'exercice.
     * 
     * @return Exercise ID
     */
    public Long getExerciseId() {
        return exerciseId;
    }

    /**
     * Sets the exercise ID.
     * Définit l'ID de l'exercice.
     * 
     * @param exerciseId - Exercise ID to set
     */
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * Gets the number of sets.
     * Obtient le nombre de séries.
     * 
     * @return Number of sets
     */
    public Integer getSetsCount() {
        return setsCount;
    }

    /**
     * Sets the number of sets.
     * Définit le nombre de séries.
     * 
     * @param setsCount - Number of sets to set
     */
    public void setSetsCount(Integer setsCount) {
        this.setsCount = setsCount;
    }

    /**
     * Gets the number of repetitions.
     * Obtient le nombre de répétitions.
     * 
     * @return Number of repetitions
     */
    public Integer getRepsCount() {
        return repsCount;
    }

    /**
     * Sets the number of repetitions.
     * Définit le nombre de répétitions.
     * 
     * @param repsCount - Number of repetitions to set
     */
    public void setRepsCount(Integer repsCount) {
        this.repsCount = repsCount;
    }

    /**
     * Gets the rest duration in seconds.
     * Obtient la durée de repos en secondes.
     * 
     * @return Rest duration in seconds
     */
    public Integer getRestDurationSeconds() {
        return restDurationSeconds;
    }

    /**
     * Sets the rest duration in seconds.
     * Définit la durée de repos en secondes.
     * 
     * @param restDurationSeconds - Rest duration to set
     */
    public void setRestDurationSeconds(Integer restDurationSeconds) {
        this.restDurationSeconds = restDurationSeconds;
    }

    /**
     * Gets the weight in kilograms.
     * Obtient le poids en kilogrammes.
     * 
     * @return Weight in kilograms
     */
    public Double getWeightKg() {
        return weightKg;
    }

    /**
     * Sets the weight in kilograms.
     * Définit le poids en kilogrammes.
     * 
     * @param weightKg - Weight to set
     */
    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    /**
     * Gets the distance in meters.
     * Obtient la distance en mètres.
     * 
     * @return Distance in meters
     */
    public Double getDistanceMeters() {
        return distanceMeters;
    }

    /**
     * Sets the distance in meters.
     * Définit la distance en mètres.
     * 
     * @param distanceMeters - Distance to set
     */
    public void setDistanceMeters(Double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    /**
     * Gets the notes.
     * Obtient les notes.
     * 
     * @return Notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     * Définit les notes.
     * 
     * @param notes - Notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CreateProgramExerciseRequest{" +
                "exerciseId=" + exerciseId +
                ", setsCount=" + setsCount +
                ", repsCount=" + repsCount +
                ", restDurationSeconds=" + restDurationSeconds +
                ", weightKg=" + weightKg +
                ", distanceMeters=" + distanceMeters +
                ", notes='" + notes + '\'' +
                '}';
    }
} 
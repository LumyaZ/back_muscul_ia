package com.example.muscul_ia.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for adding an exercise to a training program.
 * Objet de transfert de données pour ajouter un exercice à un programme d'entraînement.
 */
public class CreateProgramExerciseRequest {
    

    @NotNull(message = "L'ID de l'exercice est requis")
    private Long exerciseId;
    

    @NotNull(message = "Le nombre de séries est requis")
    @Min(value = 1, message = "Le nombre de séries doit être au moins 1")
    private Integer setsCount;
    

    @Min(value = 1, message = "Le nombre de répétitions doit être au moins 1")
    private Integer repsCount;
    

    @NotNull(message = "La durée de repos est requise")
    @Min(value = 0, message = "La durée de repos ne peut pas être négative")
    private Integer restDurationSeconds;
    

    @Min(value = 0, message = "Le poids ne peut pas être négatif")
    private Double weightKg;
    

    @Min(value = 0, message = "La distance ne peut pas être négative")
    private Double distanceMeters;
    

    private String notes;
    

    // Constructors
    public CreateProgramExerciseRequest() {}


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
    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getSetsCount() {
        return setsCount;
    }

    public void setSetsCount(Integer setsCount) {
        this.setsCount = setsCount;
    }

    public Integer getRepsCount() {
        return repsCount;
    }

    public void setRepsCount(Integer repsCount) {
        this.repsCount = repsCount;
    }

    public Integer getRestDurationSeconds() {
        return restDurationSeconds;
    }

    public void setRestDurationSeconds(Integer restDurationSeconds) {
        this.restDurationSeconds = restDurationSeconds;
    }


    public Double getWeightKg() {
        return weightKg;
    }


    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }


    public Double getDistanceMeters() {
        return distanceMeters;
    }


    public void setDistanceMeters(Double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }


    public String getNotes() {
        return notes;
    }


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
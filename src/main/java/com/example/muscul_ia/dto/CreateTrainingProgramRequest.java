package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new training program.
 * Objet de transfert de données pour créer un nouveau programme d'entraînement.
 */
public class CreateTrainingProgramRequest {

    @NotBlank(message = "Le nom du programme est requis")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    private String name;

    @NotBlank(message = "La description du programme est requise")
    @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
    private String description;

    @NotBlank(message = "La catégorie du programme est requise")
    private String category;

    @NotBlank(message = "Le niveau de difficulté est requis")
    private String difficultyLevel;

    @NotBlank(message = "Le public cible est requis")
    private String targetAudience;

    @NotNull(message = "La durée en semaines est requise")
    @Min(value = 1, message = "La durée minimale est de 1 semaine")
    @Max(value = 52, message = "La durée maximale est de 52 semaines")
    private Integer durationWeeks;

    @NotNull(message = "Le nombre de sessions par semaine est requis")
    @Min(value = 1, message = "Le nombre minimum de sessions est de 1")
    @Max(value = 7, message = "Le nombre maximum de sessions est de 7")
    private Integer sessionsPerWeek;

    @NotNull(message = "La durée estimée par session est requise")
    @Min(value = 15, message = "La durée minimale par session est de 15 minutes")
    @Max(value = 300, message = "La durée maximale par session est de 300 minutes")
    private Integer estimatedDurationMinutes;

    @NotBlank(message = "L'équipement requis est requis")
    @Size(min = 3, max = 200, message = "L'équipement doit contenir entre 3 et 200 caractères")
    private String equipmentRequired;

    @NotNull(message = "La visibilité du programme est requise")
    private Boolean isPublic;

    // Constructors
    public CreateTrainingProgramRequest() {
    }

    public CreateTrainingProgramRequest(String name, String description, String category, String difficultyLevel,
                                       String targetAudience, Integer durationWeeks, Integer sessionsPerWeek,
                                       Integer estimatedDurationMinutes, String equipmentRequired, Boolean isPublic) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.difficultyLevel = difficultyLevel;
        this.targetAudience = targetAudience;
        this.durationWeeks = durationWeeks;
        this.sessionsPerWeek = sessionsPerWeek;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.equipmentRequired = equipmentRequired;
        this.isPublic = isPublic;
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


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public String getDifficultyLevel() {
        return difficultyLevel;
    }


    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }


    public String getTargetAudience() {
        return targetAudience;
    }


    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }


    public Integer getDurationWeeks() {
        return durationWeeks;
    }


    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }


    public Integer getSessionsPerWeek() {
        return sessionsPerWeek;
    }


    public void setSessionsPerWeek(Integer sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }


    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }


    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }


    public String getEquipmentRequired() {
        return equipmentRequired;
    }


    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }


    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "CreateTrainingProgramRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", durationWeeks=" + durationWeeks +
                ", sessionsPerWeek=" + sessionsPerWeek +
                ", estimatedDurationMinutes=" + estimatedDurationMinutes +
                ", equipmentRequired='" + equipmentRequired + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
} 
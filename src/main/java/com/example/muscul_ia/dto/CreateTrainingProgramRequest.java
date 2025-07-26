package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating a new training program.
 * Objet de transfert de données pour créer un nouveau programme d'entraînement.
 * 
 * This DTO contains all the necessary information to create a new training program
 * including basic details, scheduling information, and visibility settings.
 * 
 * Ce DTO contient toutes les informations nécessaires pour créer un nouveau
 * programme d'entraînement incluant les détails de base, les informations de
 * planification et les paramètres de visibilité.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CreateTrainingProgramRequest {

    /**
     * Name of the training program.
     * Nom du programme d'entraînement.
     */
    @NotBlank(message = "Le nom du programme est requis")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    private String name;

    /**
     * Description of the training program.
     * Description du programme d'entraînement.
     */
    @NotBlank(message = "La description du programme est requise")
    @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
    private String description;

    /**
     * Category of the training program (e.g., Musculation, Cardio, Flexibilité, Mixte).
     * Catégorie du programme d'entraînement (ex: Musculation, Cardio, Flexibilité, Mixte).
     */
    @NotBlank(message = "La catégorie du programme est requise")
    private String category;

    /**
     * Difficulty level of the training program (e.g., Débutant, Intermédiaire, Avancé).
     * Niveau de difficulté du programme d'entraînement (ex: Débutant, Intermédiaire, Avancé).
     */
    @NotBlank(message = "Le niveau de difficulté est requis")
    private String difficultyLevel;

    /**
     * Target audience for the training program.
     * Public cible pour le programme d'entraînement.
     */
    @NotBlank(message = "Le public cible est requis")
    private String targetAudience;

    /**
     * Duration of the training program in weeks.
     * Durée du programme d'entraînement en semaines.
     */
    @NotNull(message = "La durée en semaines est requise")
    @Min(value = 1, message = "La durée minimale est de 1 semaine")
    @Max(value = 52, message = "La durée maximale est de 52 semaines")
    private Integer durationWeeks;

    /**
     * Number of training sessions per week.
     * Nombre de sessions d'entraînement par semaine.
     */
    @NotNull(message = "Le nombre de sessions par semaine est requis")
    @Min(value = 1, message = "Le nombre minimum de sessions est de 1")
    @Max(value = 7, message = "Le nombre maximum de sessions est de 7")
    private Integer sessionsPerWeek;

    /**
     * Estimated duration of each training session in minutes.
     * Durée estimée de chaque session d'entraînement en minutes.
     */
    @NotNull(message = "La durée estimée par session est requise")
    @Min(value = 15, message = "La durée minimale par session est de 15 minutes")
    @Max(value = 300, message = "La durée maximale par session est de 300 minutes")
    private Integer estimatedDurationMinutes;

    /**
     * Equipment required for the training program.
     * Équipement requis pour le programme d'entraînement.
     */
    @NotBlank(message = "L'équipement requis est requis")
    @Size(min = 3, max = 200, message = "L'équipement doit contenir entre 3 et 200 caractères")
    private String equipmentRequired;

    /**
     * Whether the training program is public or private.
     * Si le programme d'entraînement est public ou privé.
     */
    @NotNull(message = "La visibilité du programme est requise")
    private Boolean isPublic;

    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public CreateTrainingProgramRequest() {
    }

    /**
     * Constructor with all parameters.
     * Constructeur avec tous les paramètres.
     * 
     * @param name - Name of the training program
     * @param description - Description of the training program
     * @param category - Category of the training program
     * @param difficultyLevel - Difficulty level of the training program
     * @param targetAudience - Target audience for the training program
     * @param durationWeeks - Duration of the training program in weeks
     * @param sessionsPerWeek - Number of training sessions per week
     * @param estimatedDurationMinutes - Estimated duration of each training session in minutes
     * @param equipmentRequired - Equipment required for the training program
     * @param isPublic - Whether the training program is public or private
     */
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

    /**
     * Gets the name of the training program.
     * Obtient le nom du programme d'entraînement.
     * 
     * @return Name of the training program
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the training program.
     * Définit le nom du programme d'entraînement.
     * 
     * @param name - Name of the training program
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the training program.
     * Obtient la description du programme d'entraînement.
     * 
     * @return Description of the training program
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the training program.
     * Définit la description du programme d'entraînement.
     * 
     * @param description - Description of the training program
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category of the training program.
     * Obtient la catégorie du programme d'entraînement.
     * 
     * @return Category of the training program
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the training program.
     * Définit la catégorie du programme d'entraînement.
     * 
     * @param category - Category of the training program
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the difficulty level of the training program.
     * Obtient le niveau de difficulté du programme d'entraînement.
     * 
     * @return Difficulty level of the training program
     */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level of the training program.
     * Définit le niveau de difficulté du programme d'entraînement.
     * 
     * @param difficultyLevel - Difficulty level of the training program
     */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Gets the target audience for the training program.
     * Obtient le public cible pour le programme d'entraînement.
     * 
     * @return Target audience for the training program
     */
    public String getTargetAudience() {
        return targetAudience;
    }

    /**
     * Sets the target audience for the training program.
     * Définit le public cible pour le programme d'entraînement.
     * 
     * @param targetAudience - Target audience for the training program
     */
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    /**
     * Gets the duration of the training program in weeks.
     * Obtient la durée du programme d'entraînement en semaines.
     * 
     * @return Duration of the training program in weeks
     */
    public Integer getDurationWeeks() {
        return durationWeeks;
    }

    /**
     * Sets the duration of the training program in weeks.
     * Définit la durée du programme d'entraînement en semaines.
     * 
     * @param durationWeeks - Duration of the training program in weeks
     */
    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    /**
     * Gets the number of training sessions per week.
     * Obtient le nombre de sessions d'entraînement par semaine.
     * 
     * @return Number of training sessions per week
     */
    public Integer getSessionsPerWeek() {
        return sessionsPerWeek;
    }

    /**
     * Sets the number of training sessions per week.
     * Définit le nombre de sessions d'entraînement par semaine.
     * 
     * @param sessionsPerWeek - Number of training sessions per week
     */
    public void setSessionsPerWeek(Integer sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }

    /**
     * Gets the estimated duration of each training session in minutes.
     * Obtient la durée estimée de chaque session d'entraînement en minutes.
     * 
     * @return Estimated duration of each training session in minutes
     */
    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    /**
     * Sets the estimated duration of each training session in minutes.
     * Définit la durée estimée de chaque session d'entraînement en minutes.
     * 
     * @param estimatedDurationMinutes - Estimated duration of each training session in minutes
     */
    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }

    /**
     * Gets the equipment required for the training program.
     * Obtient l'équipement requis pour le programme d'entraînement.
     * 
     * @return Equipment required for the training program
     */
    public String getEquipmentRequired() {
        return equipmentRequired;
    }

    /**
     * Sets the equipment required for the training program.
     * Définit l'équipement requis pour le programme d'entraînement.
     * 
     * @param equipmentRequired - Equipment required for the training program
     */
    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }

    /**
     * Gets whether the training program is public or private.
     * Obtient si le programme d'entraînement est public ou privé.
     * 
     * @return Whether the training program is public or private
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * Sets whether the training program is public or private.
     * Définit si le programme d'entraînement est public ou privé.
     * 
     * @param isPublic - Whether the training program is public or private
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Returns a string representation of the CreateTrainingProgramRequest.
     * Retourne une représentation en chaîne de caractères du CreateTrainingProgramRequest.
     * 
     * @return String representation of the object
     */
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
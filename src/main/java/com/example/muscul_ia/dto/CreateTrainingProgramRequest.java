package com.example.muscul_ia.dto;

import java.util.List;

/**
 * Data Transfer Object for creating new training programs.
 * Objet de transfert de données pour la création de nouveaux programmes d'entraînement.
 * 
 * This DTO is used to transfer training program creation data from the client to the server.
 * It contains all the necessary information required to create a new training program
 * including program details, difficulty level, duration, target audience, equipment
 * requirements, and a list of exercises with their specific parameters.
 * 
 * Ce DTO est utilisé pour transférer les données de création de programme d'entraînement
 * du client vers le serveur. Il contient toutes les informations nécessaires pour créer
 * un nouveau programme d'entraînement incluant les détails du programme, le niveau de
 * difficulté, la durée, l'audience cible, les équipements requis et une liste d'exercices
 * avec leurs paramètres spécifiques.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CreateTrainingProgramRequest {
    
    /**
     * Name of the training program to be created.
     * Nom du programme d'entraînement à créer.
     */
    private String name;
    
    /**
     * Detailed description of the training program.
     * Description détaillée du programme d'entraînement.
     */
    private String description;
    
    /**
     * Difficulty level of the program (e.g., "Débutant", "Intermédiaire", "Avancé").
     * Niveau de difficulté du programme (ex: "Débutant", "Intermédiaire", "Avancé").
     */
    private String difficultyLevel;
    
    /**
     * Duration of the program in weeks.
     * Durée du programme en semaines.
     */
    private Integer durationWeeks;
    
    /**
     * Number of training sessions per week.
     * Nombre de sessions d'entraînement par semaine.
     */
    private Integer sessionsPerWeek;
    
    /**
     * Estimated duration of each session in minutes.
     * Durée estimée de chaque session en minutes.
     */
    private Integer estimatedDurationMinutes;
    
    /**
     * Category of the training program (e.g., "Musculation", "Cardio", "Mixte").
     * Catégorie du programme d'entraînement (ex: "Musculation", "Cardio", "Mixte").
     */
    private String category;
    
    /**
     * Target audience for the program (e.g., "Débutants", "Sportifs confirmés").
     * Audience cible pour le programme (ex: "Débutants", "Sportifs confirmés").
     */
    private String targetAudience;
    
    /**
     * Equipment required to follow the program.
     * Équipement requis pour suivre le programme.
     */
    private String equipmentRequired;
    
    /**
     * URL to an image representing the program.
     * URL vers une image représentant le programme.
     */
    private String imageUrl;
    
    /**
     * Flag indicating if the program should be public and visible to all users.
     * Indicateur indiquant si le programme doit être public et visible par tous les utilisateurs.
     */
    private Boolean isPublic;
    
    /**
     * List of exercises included in the training program with their specific parameters.
     * Liste des exercices inclus dans le programme d'entraînement avec leurs paramètres spécifiques.
     */
    private List<ProgramExerciseRequest> exercises;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public CreateTrainingProgramRequest() {}
    
    // Getters et Setters
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
     * Get the list of exercises in the program.
     * Récupérer la liste des exercices du programme.
     * 
     * @return List<ProgramExerciseRequest> - List of exercises
     */
    public List<ProgramExerciseRequest> getExercises() {
        return exercises;
    }
    
    /**
     * Set the list of exercises in the program.
     * Définir la liste des exercices du programme.
     * 
     * @param exercises - List of exercises
     */
    public void setExercises(List<ProgramExerciseRequest> exercises) {
        this.exercises = exercises;
    }
    
    /**
     * Inner class representing an exercise within a training program.
     * Classe interne représentant un exercice dans un programme d'entraînement.
     * 
     * This class contains all the specific parameters for an exercise within
     * a training program, including sets, reps, duration, rest periods,
     * weights, and optional notes.
     * 
     * Cette classe contient tous les paramètres spécifiques pour un exercice
     * dans un programme d'entraînement, incluant les séries, répétitions,
     * durée, périodes de repos, poids et notes optionnelles.
     * 
     * @author Muscul IA Team
     * @version 1.0
     * @since 2024-01-01
     */
    public static class ProgramExerciseRequest {
        
        /**
         * ID of the exercise to include in the program.
         * ID de l'exercice à inclure dans le programme.
         */
        private Long exerciseId;
        
        /**
         * Order of the exercise within the program (1, 2, 3, etc.).
         * Ordre de l'exercice dans le programme (1, 2, 3, etc.).
         */
        private Integer orderInProgram;
        
        /**
         * Number of sets to perform for this exercise.
         * Nombre de séries à effectuer pour cet exercice.
         */
        private Integer setsCount;
        
        /**
         * Number of repetitions per set.
         * Nombre de répétitions par série.
         */
        private Integer repsCount;
        
        /**
         * Duration of the exercise in seconds (for time-based exercises).
         * Durée de l'exercice en secondes (pour les exercices basés sur le temps).
         */
        private Integer durationSeconds;
        
        /**
         * Rest duration between sets in seconds.
         * Durée de repos entre les séries en secondes.
         */
        private Integer restDurationSeconds;
        
        /**
         * Weight to use for the exercise in kilograms.
         * Poids à utiliser pour l'exercice en kilogrammes.
         */
        private Double weightKg;
        
        /**
         * Distance to cover for the exercise in meters (for cardio exercises).
         * Distance à parcourir pour l'exercice en mètres (pour les exercices cardio).
         */
        private Double distanceMeters;
        
        /**
         * Additional notes or instructions for the exercise.
         * Notes ou instructions supplémentaires pour l'exercice.
         */
        private String notes;
        
        /**
         * Flag indicating if this exercise is optional in the program.
         * Indicateur indiquant si cet exercice est optionnel dans le programme.
         */
        private Boolean isOptional;
        
        /**
         * Default constructor.
         * Constructeur par défaut.
         */
        public ProgramExerciseRequest() {}
        
        // Getters et Setters
        /**
         * Get the exercise ID.
         * Récupérer l'ID de l'exercice.
         * 
         * @return Long - Exercise ID
         */
        public Long getExerciseId() {
            return exerciseId;
        }
        
        /**
         * Set the exercise ID.
         * Définir l'ID de l'exercice.
         * 
         * @param exerciseId - Exercise ID
         */
        public void setExerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
        }
        
        /**
         * Get the order in program.
         * Récupérer l'ordre dans le programme.
         * 
         * @return Integer - Order in program
         */
        public Integer getOrderInProgram() {
            return orderInProgram;
        }
        
        /**
         * Set the order in program.
         * Définir l'ordre dans le programme.
         * 
         * @param orderInProgram - Order in program
         */
        public void setOrderInProgram(Integer orderInProgram) {
            this.orderInProgram = orderInProgram;
        }
        
        /**
         * Get the number of sets.
         * Récupérer le nombre de séries.
         * 
         * @return Integer - Number of sets
         */
        public Integer getSetsCount() {
            return setsCount;
        }
        
        /**
         * Set the number of sets.
         * Définir le nombre de séries.
         * 
         * @param setsCount - Number of sets
         */
        public void setSetsCount(Integer setsCount) {
            this.setsCount = setsCount;
        }
        
        /**
         * Get the number of repetitions.
         * Récupérer le nombre de répétitions.
         * 
         * @return Integer - Number of repetitions
         */
        public Integer getRepsCount() {
            return repsCount;
        }
        
        /**
         * Set the number of repetitions.
         * Définir le nombre de répétitions.
         * 
         * @param repsCount - Number of repetitions
         */
        public void setRepsCount(Integer repsCount) {
            this.repsCount = repsCount;
        }
        
        /**
         * Get the duration in seconds.
         * Récupérer la durée en secondes.
         * 
         * @return Integer - Duration in seconds
         */
        public Integer getDurationSeconds() {
            return durationSeconds;
        }
        
        /**
         * Set the duration in seconds.
         * Définir la durée en secondes.
         * 
         * @param durationSeconds - Duration in seconds
         */
        public void setDurationSeconds(Integer durationSeconds) {
            this.durationSeconds = durationSeconds;
        }
        
        /**
         * Get the rest duration in seconds.
         * Récupérer la durée de repos en secondes.
         * 
         * @return Integer - Rest duration in seconds
         */
        public Integer getRestDurationSeconds() {
            return restDurationSeconds;
        }
        
        /**
         * Set the rest duration in seconds.
         * Définir la durée de repos en secondes.
         * 
         * @param restDurationSeconds - Rest duration in seconds
         */
        public void setRestDurationSeconds(Integer restDurationSeconds) {
            this.restDurationSeconds = restDurationSeconds;
        }
        
        /**
         * Get the weight in kilograms.
         * Récupérer le poids en kilogrammes.
         * 
         * @return Double - Weight in kilograms
         */
        public Double getWeightKg() {
            return weightKg;
        }
        
        /**
         * Set the weight in kilograms.
         * Définir le poids en kilogrammes.
         * 
         * @param weightKg - Weight in kilograms
         */
        public void setWeightKg(Double weightKg) {
            this.weightKg = weightKg;
        }
        
        /**
         * Get the distance in meters.
         * Récupérer la distance en mètres.
         * 
         * @return Double - Distance in meters
         */
        public Double getDistanceMeters() {
            return distanceMeters;
        }
        
        /**
         * Set the distance in meters.
         * Définir la distance en mètres.
         * 
         * @param distanceMeters - Distance in meters
         */
        public void setDistanceMeters(Double distanceMeters) {
            this.distanceMeters = distanceMeters;
        }
        
        /**
         * Get the notes for the exercise.
         * Récupérer les notes pour l'exercice.
         * 
         * @return String - Exercise notes
         */
        public String getNotes() {
            return notes;
        }
        
        /**
         * Set the notes for the exercise.
         * Définir les notes pour l'exercice.
         * 
         * @param notes - Exercise notes
         */
        public void setNotes(String notes) {
            this.notes = notes;
        }
        
        /**
         * Check if the exercise is optional.
         * Vérifier si l'exercice est optionnel.
         * 
         * @return Boolean - True if optional, false otherwise
         */
        public Boolean getIsOptional() {
            return isOptional;
        }
        
        /**
         * Set if the exercise is optional.
         * Définir si l'exercice est optionnel.
         * 
         * @param isOptional - Optional status
         */
        public void setIsOptional(Boolean isOptional) {
            this.isOptional = isOptional;
        }
    }
} 
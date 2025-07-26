package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for creating new exercises.
 * Objet de transfert de données pour la création de nouveaux exercices.
 * 
 * This DTO is used to transfer exercise creation data from the client to the server.
 * It contains all the necessary information required to create a new exercise
 * including name, description, category, muscle group, equipment requirements,
 * difficulty level, and media URLs.
 * 
 * Ce DTO est utilisé pour transférer les données de création d'exercice du client
 * vers le serveur. Il contient toutes les informations nécessaires pour créer
 * un nouvel exercice incluant le nom, la description, la catégorie, le groupe
 * musculaire, les équipements requis, le niveau de difficulté et les URLs média.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class CreateExerciseRequest {
    
    /**
     * Name of the exercise to be created.
     * Nom de l'exercice à créer.
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
     * URL to a video demonstrating the exercise.
     * URL vers une vidéo démontrant l'exercice.
     */
    private String videoUrl;
    
    /**
     * URL to an image showing the exercise.
     * URL vers une image montrant l'exercice.
     */
    private String imageUrl;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public CreateExerciseRequest() {}
    
    /**
     * Constructor with all exercise creation parameters.
     * Constructeur avec tous les paramètres de création d'exercice.
     * 
     * @param name - Exercise name
     * @param description - Exercise description
     * @param category - Exercise category
     * @param muscleGroup - Target muscle group
     * @param equipmentNeeded - Required equipment
     * @param difficultyLevel - Difficulty level
     * @param videoUrl - Video demonstration URL
     * @param imageUrl - Exercise image URL
     */
    public CreateExerciseRequest(String name, String description, String category,
                                String muscleGroup, String equipmentNeeded, String difficultyLevel,
                                String videoUrl, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroup = muscleGroup;
        this.equipmentNeeded = equipmentNeeded;
        this.difficultyLevel = difficultyLevel;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
    }
    
    // Getters et Setters
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
} 
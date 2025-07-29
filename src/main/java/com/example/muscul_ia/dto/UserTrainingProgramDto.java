package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for UserTrainingProgram entity.
 * Objet de transfert de données pour l'entité UserTrainingProgram.
 * 
 * This DTO represents the simple relationship between a user and a training program.
 * 
 * Ce DTO représente la relation simple entre un utilisateur et un programme d'entraînement.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class UserTrainingProgramDto {
    
    /**
     * Unique identifier for the user-program relationship.
     * Identifiant unique de la relation utilisateur-programme.
     */
    private Long id;
    
    /**
     * User information.
     * Informations sur l'utilisateur.
     */
    private UserDto user;
    
    /**
     * Training program information.
     * Informations sur le programme d'entraînement.
     */
    private TrainingProgramDto trainingProgram;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public UserTrainingProgramDto() {}
    
    /**
     * Constructor with all fields.
     * Constructeur avec tous les champs.
     * 
     * @param id - Relationship ID
     * @param user - User information
     * @param trainingProgram - Training program information
     */
    public UserTrainingProgramDto(Long id, UserDto user, TrainingProgramDto trainingProgram) {
        this.id = id;
        this.user = user;
        this.trainingProgram = trainingProgram;
    }
    
    // Getters and Setters
    /**
     * Get the relationship ID.
     * Récupérer l'ID de la relation.
     * 
     * @return Long - Relationship ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Set the relationship ID.
     * Définir l'ID de la relation.
     * 
     * @param id - Relationship ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the user information.
     * Récupérer les informations sur l'utilisateur.
     * 
     * @return UserDto - User information
     */
    public UserDto getUser() {
        return user;
    }
    
    /**
     * Set the user information.
     * Définir les informations sur l'utilisateur.
     * 
     * @param user - User information
     */
    public void setUser(UserDto user) {
        this.user = user;
    }
    
    /**
     * Get the training program information.
     * Récupérer les informations sur le programme d'entraînement.
     * 
     * @return TrainingProgramDto - Training program information
     */
    public TrainingProgramDto getTrainingProgram() {
        return trainingProgram;
    }
    
    /**
     * Set the training program information.
     * Définir les informations sur le programme d'entraînement.
     * 
     * @param trainingProgram - Training program information
     */
    public void setTrainingProgram(TrainingProgramDto trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
} 
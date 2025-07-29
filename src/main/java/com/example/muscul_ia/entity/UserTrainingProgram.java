package com.example.muscul_ia.entity;

import jakarta.persistence.*;

/**
 * Entity representing the relationship between a user and a training program.
 * Entité représentant la relation entre un utilisateur et un programme d'entraînement.
 * 
 * This entity creates a simple many-to-many relationship between users and training programs,
 * allowing users to subscribe to multiple programs and programs to have multiple users.
 * 
 * Cette entité crée une relation many-to-many simple entre les utilisateurs et les programmes
 * d'entraînement, permettant aux utilisateurs de s'abonner à plusieurs programmes et
 * aux programmes d'avoir plusieurs utilisateurs.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "user_training_programs")
public class UserTrainingProgram {
    
    /**
     * Unique identifier for the user-program relationship.
     * Identifiant unique de la relation utilisateur-programme.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User who is following the training program.
     * Utilisateur qui suit le programme d'entraînement.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * Training program that the user is following.
     * Programme d'entraînement que l'utilisateur suit.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;
    
    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public UserTrainingProgram() {}
    
    /**
     * Constructor with user and training program.
     * Constructeur avec utilisateur et programme d'entraînement.
     * 
     * @param user - User following the program
     * @param trainingProgram - Training program being followed
     */
    public UserTrainingProgram(User user, TrainingProgram trainingProgram) {
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
     * Get the user following the program.
     * Récupérer l'utilisateur qui suit le programme.
     * 
     * @return User - User following the program
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Set the user following the program.
     * Définir l'utilisateur qui suit le programme.
     * 
     * @param user - User following the program
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Get the training program being followed.
     * Récupérer le programme d'entraînement suivi.
     * 
     * @return TrainingProgram - Training program being followed
     */
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }
    
    /**
     * Set the training program being followed.
     * Définir le programme d'entraînement suivi.
     * 
     * @param trainingProgram - Training program being followed
     */
    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
} 
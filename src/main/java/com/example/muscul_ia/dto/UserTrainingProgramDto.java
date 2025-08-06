package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for UserTrainingProgram entity.
 * Objet de transfert de données pour l'entité UserTrainingProgram.
 */
public class UserTrainingProgramDto {
    
    private Long id;
    
    private UserDto user;
    
    private TrainingProgramDto trainingProgram;
    
    public UserTrainingProgramDto() {}
    
    // Constructor
    public UserTrainingProgramDto(Long id, UserDto user, TrainingProgramDto trainingProgram) {
        this.id = id;
        this.user = user;
        this.trainingProgram = trainingProgram;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public UserDto getUser() {
        return user;
    }
    
    public void setUser(UserDto user) {
        this.user = user;
    }
    
    public TrainingProgramDto getTrainingProgram() {
        return trainingProgram;
    }
    
    public void setTrainingProgram(TrainingProgramDto trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
} 
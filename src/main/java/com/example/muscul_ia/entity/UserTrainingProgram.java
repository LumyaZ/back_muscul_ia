package com.example.muscul_ia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_training_programs")
public class UserTrainingProgram {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;
    
    public UserTrainingProgram() {}
    
    public UserTrainingProgram(User user, TrainingProgram trainingProgram) {
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }
            
    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
} 
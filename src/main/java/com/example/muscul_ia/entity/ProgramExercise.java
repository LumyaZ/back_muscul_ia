package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity class for program exercises.
 * Entité pour les exercices de programme.
 */
@Entity
@Table(name = "program_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProgramExercise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_program_id", nullable = false)
    private TrainingProgram trainingProgram;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
    
    @Column(name = "sets_count")
    private Integer setsCount;
    
    @Column(name = "reps_count")
    private Integer repsCount;
     
    @Column(name = "rest_duration_seconds")
    private Integer restDurationSeconds;
    
    @Column(name = "weight_kg")
    private Double weightKg;
    
    @Column(name = "distance_meters")
    private Double distanceMeters;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructeur
    public ProgramExercise(TrainingProgram trainingProgram, Exercise exercise) {
        this.trainingProgram = trainingProgram;
        this.exercise = exercise;
        this.createdAt = LocalDateTime.now();
    }
    

    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 
package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class for program exercises.
 * Entité pour les exercices de programme.
 */
@Entity
@Table(name = "program_exercises")
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
    
    // Constructeurs
    public ProgramExercise() {
        this.createdAt = LocalDateTime.now();
    }
    
    public ProgramExercise(TrainingProgram trainingProgram, Exercise exercise) {
        this();
        this.trainingProgram = trainingProgram;
        this.exercise = exercise;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }
    
    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
    
    public Exercise getExercise() {
        return exercise;
    }
    
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 
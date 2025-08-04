package com.example.muscul_ia.dto;

import java.time.LocalDateTime;

public class ProgramExerciseDto {
    private Long id;
    private Long trainingProgramId;
    private Long exerciseId;
    private String exerciseName;
    private String exerciseDescription;
    private String exerciseCategory;
    private String exerciseMuscleGroup;
    private String exerciseEquipmentNeeded;
    private String exerciseDifficultyLevel;
    private Integer setsCount;
    private Integer repsCount;
    private Integer restDurationSeconds;
    private Double weightKg;
    private Double distanceMeters;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructeurs
    public ProgramExerciseDto() {}
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    
    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }
    
    public Long getExerciseId() {
        return exerciseId;
    }
    
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
    
    public String getExerciseName() {
        return exerciseName;
    }
    
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
    
    public String getExerciseDescription() {
        return exerciseDescription;
    }
    
    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }
    
    public String getExerciseCategory() {
        return exerciseCategory;
    }
    
    public void setExerciseCategory(String exerciseCategory) {
        this.exerciseCategory = exerciseCategory;
    }
    
    public String getExerciseMuscleGroup() {
        return exerciseMuscleGroup;
    }
    
    public void setExerciseMuscleGroup(String exerciseMuscleGroup) {
        this.exerciseMuscleGroup = exerciseMuscleGroup;
    }
    
    public String getExerciseEquipmentNeeded() {
        return exerciseEquipmentNeeded;
    }
    
    public void setExerciseEquipmentNeeded(String exerciseEquipmentNeeded) {
        this.exerciseEquipmentNeeded = exerciseEquipmentNeeded;
    }
    
    public String getExerciseDifficultyLevel() {
        return exerciseDifficultyLevel;
    }
    
    public void setExerciseDifficultyLevel(String exerciseDifficultyLevel) {
        this.exerciseDifficultyLevel = exerciseDifficultyLevel;
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
} 
package com.example.muscul_ia.dto;

import java.util.List;

public class CreateTrainingProgramRequest {
    private String name;
    private String description;
    private String difficultyLevel;
    private Integer durationWeeks;
    private Integer sessionsPerWeek;
    private Integer estimatedDurationMinutes;
    private String category;
    private String targetAudience;
    private String equipmentRequired;
    private String imageUrl;
    private Boolean isPublic;
    private List<ProgramExerciseRequest> exercises;
    
    // Constructeur par défaut
    public CreateTrainingProgramRequest() {}
    
    // Getters et Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    
    public Integer getDurationWeeks() {
        return durationWeeks;
    }
    
    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }
    
    public Integer getSessionsPerWeek() {
        return sessionsPerWeek;
    }
    
    public void setSessionsPerWeek(Integer sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }
    
    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }
    
    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getTargetAudience() {
        return targetAudience;
    }
    
    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }
    
    public String getEquipmentRequired() {
        return equipmentRequired;
    }
    
    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public List<ProgramExerciseRequest> getExercises() {
        return exercises;
    }
    
    public void setExercises(List<ProgramExerciseRequest> exercises) {
        this.exercises = exercises;
    }
    
    // Classe interne pour les exercices du programme
    public static class ProgramExerciseRequest {
        private Long exerciseId;
        private Integer orderInProgram;
        private Integer setsCount;
        private Integer repsCount;
        private Integer durationSeconds;
        private Integer restDurationSeconds;
        private Double weightKg;
        private Double distanceMeters;
        private String notes;
        private Boolean isOptional;
        
        // Constructeur par défaut
        public ProgramExerciseRequest() {}
        
        // Getters et Setters
        public Long getExerciseId() {
            return exerciseId;
        }
        
        public void setExerciseId(Long exerciseId) {
            this.exerciseId = exerciseId;
        }
        
        public Integer getOrderInProgram() {
            return orderInProgram;
        }
        
        public void setOrderInProgram(Integer orderInProgram) {
            this.orderInProgram = orderInProgram;
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
        
        public Integer getDurationSeconds() {
            return durationSeconds;
        }
        
        public void setDurationSeconds(Integer durationSeconds) {
            this.durationSeconds = durationSeconds;
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
        
        public Boolean getIsOptional() {
            return isOptional;
        }
        
        public void setIsOptional(Boolean isOptional) {
            this.isOptional = isOptional;
        }
    }
} 
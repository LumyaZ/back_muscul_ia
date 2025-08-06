package com.example.muscul_ia.dto;

/**
 * Data Transfer Object for creating new exercises.
 * Objet de transfert de données pour la création de nouveaux exercices. 
 */ 
public class CreateExerciseRequest {
    
    private String name;
    
    private String description;
    
    private String category;
    
    private String muscleGroup;
    
    private String equipmentNeeded;
    
    private String difficultyLevel;
    
    // Constructors
    public CreateExerciseRequest() {}
    

    public CreateExerciseRequest(String name, String description, String category,
                                String muscleGroup, String equipmentNeeded, String difficultyLevel
                                ) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroup = muscleGroup;
        this.equipmentNeeded = equipmentNeeded;
        this.difficultyLevel = difficultyLevel;
    }
    
    // Getters and Setters
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
    

    public String getCategory() {
        return category;
    }
    

    public void setCategory(String category) {
        this.category = category;
    }
    

    public String getMuscleGroup() {
        return muscleGroup;
    }
    

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
    

    public String getEquipmentNeeded() {
        return equipmentNeeded;
    }
    

    public void setEquipmentNeeded(String equipmentNeeded) {
        this.equipmentNeeded = equipmentNeeded;
    }
    

    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
} 
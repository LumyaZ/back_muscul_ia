package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for updating training information.
 * DTO pour mettre à jour les informations d'entraînement.
 */ 
public class UpdateTrainingInfoRequest {
    
    private String gender;
    
    @DecimalMin(value = "30.0", message = "Le poids doit être d'au moins 30 kg")
    @DecimalMax(value = "300.0", message = "Le poids ne peut pas dépasser 300 kg")
    private Double weight;
    
    @DecimalMin(value = "100.0", message = "La taille doit être d'au moins 100 cm")
    @DecimalMax(value = "250.0", message = "La taille ne peut pas dépasser 250 cm")
    private Double height;
    
    @DecimalMin(value = "3.0", message = "Le pourcentage de graisse corporelle doit être d'au moins 3%")
    @DecimalMax(value = "50.0", message = "Le pourcentage de graisse corporelle ne peut pas dépasser 50%")
    private Double bodyFatPercentage;
    
    private String experienceLevel;
    
    private String sessionFrequency;
    
    private String sessionDuration;
    
    private String mainGoal;
    
    private String trainingPreference;
    
    private String equipment;

    // Constructors
    public UpdateTrainingInfoRequest() {}

    public UpdateTrainingInfoRequest(String gender, Double weight, Double height, Double bodyFatPercentage,
                                   String experienceLevel, String sessionFrequency,
                                   String sessionDuration, String mainGoal,
                                   String trainingPreference, String equipment) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bodyFatPercentage = bodyFatPercentage;
        this.experienceLevel = experienceLevel;
        this.sessionFrequency = sessionFrequency;
        this.sessionDuration = sessionDuration;
        this.mainGoal = mainGoal;
        this.trainingPreference = trainingPreference;
        this.equipment = equipment;
    }

    // Getters and Setters
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public Double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(Double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }
    
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
    
    public String getSessionFrequency() { return sessionFrequency; }
    public void setSessionFrequency(String sessionFrequency) { this.sessionFrequency = sessionFrequency; }
    
    public String getSessionDuration() { return sessionDuration; }
    public void setSessionDuration(String sessionDuration) { this.sessionDuration = sessionDuration; }
    
    public String getMainGoal() { return mainGoal; }
    public void setMainGoal(String mainGoal) { this.mainGoal = mainGoal; }
    
    public String getTrainingPreference() { return trainingPreference; }
    public void setTrainingPreference(String trainingPreference) { this.trainingPreference = trainingPreference; }
    
    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    @Override
    public String toString() {
        return "UpdateTrainingInfoRequest{" +
                "gender=" + gender +
                ", weight=" + weight +
                ", height=" + height +
                ", bodyFatPercentage=" + bodyFatPercentage +
                ", experienceLevel=" + experienceLevel +
                ", sessionFrequency=" + sessionFrequency +
                ", sessionDuration=" + sessionDuration +
                ", mainGoal=" + mainGoal +
                ", trainingPreference=" + trainingPreference +
                ", equipment=" + equipment +
                '}';
    }
} 
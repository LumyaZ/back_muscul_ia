package com.example.muscul_ia.dto;

import com.example.muscul_ia.enums.*;
import jakarta.validation.constraints.*;

public class UpdateTrainingInfoRequest {
    
    private Gender gender;
    
    @DecimalMin(value = "30.0", message = "Le poids doit être d'au moins 30 kg")
    @DecimalMax(value = "300.0", message = "Le poids ne peut pas dépasser 300 kg")
    private Double weight;
    
    @DecimalMin(value = "100.0", message = "La taille doit être d'au moins 100 cm")
    @DecimalMax(value = "250.0", message = "La taille ne peut pas dépasser 250 cm")
    private Double height;
    
    @DecimalMin(value = "3.0", message = "Le pourcentage de graisse corporelle doit être d'au moins 3%")
    @DecimalMax(value = "50.0", message = "Le pourcentage de graisse corporelle ne peut pas dépasser 50%")
    private Double bodyFatPercentage;
    
    private ExperienceLevel experienceLevel;
    
    private SessionFrequency sessionFrequency;
    
    private SessionDuration sessionDuration;
    
    private MainGoal mainGoal;
    
    private TrainingPreference trainingPreference;
    
    private Equipment equipment;

    public UpdateTrainingInfoRequest() {}

    public UpdateTrainingInfoRequest(Gender gender, Double weight, Double height, Double bodyFatPercentage,
                                   ExperienceLevel experienceLevel, SessionFrequency sessionFrequency,
                                   SessionDuration sessionDuration, MainGoal mainGoal,
                                   TrainingPreference trainingPreference, Equipment equipment) {
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
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public Double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(Double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }
    
    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; }
    
    public SessionFrequency getSessionFrequency() { return sessionFrequency; }
    public void setSessionFrequency(SessionFrequency sessionFrequency) { this.sessionFrequency = sessionFrequency; }
    
    public SessionDuration getSessionDuration() { return sessionDuration; }
    public void setSessionDuration(SessionDuration sessionDuration) { this.sessionDuration = sessionDuration; }
    
    public MainGoal getMainGoal() { return mainGoal; }
    public void setMainGoal(MainGoal mainGoal) { this.mainGoal = mainGoal; }
    
    public TrainingPreference getTrainingPreference() { return trainingPreference; }
    public void setTrainingPreference(TrainingPreference trainingPreference) { this.trainingPreference = trainingPreference; }
    
    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }

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
package com.example.muscul_ia.dto;

import com.example.muscul_ia.enums.*;
import jakarta.validation.constraints.*;

public class CreateTrainingInfoRequest {
    
    @NotNull(message = "Le genre est obligatoire")
    private Gender gender;
    
    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "30.0", message = "Le poids doit être d'au moins 30 kg")
    @DecimalMax(value = "300.0", message = "Le poids ne peut pas dépasser 300 kg")
    private Double weight;
    
    @NotNull(message = "La taille est obligatoire")
    @DecimalMin(value = "100.0", message = "La taille doit être d'au moins 100 cm")
    @DecimalMax(value = "250.0", message = "La taille ne peut pas dépasser 250 cm")
    private Double height;
    
    @DecimalMin(value = "3.0", message = "Le pourcentage de graisse corporelle doit être d'au moins 3%")
    @DecimalMax(value = "50.0", message = "Le pourcentage de graisse corporelle ne peut pas dépasser 50%")
    private Double bodyFatPercentage;
    
    @NotNull(message = "Le niveau d'expérience est obligatoire")
    private ExperienceLevel experienceLevel;
    
    @NotNull(message = "La fréquence des sessions est obligatoire")
    private SessionFrequency sessionFrequency;
    
    @NotNull(message = "La durée des sessions est obligatoire")
    private SessionDuration sessionDuration;
    
    @NotNull(message = "L'objectif principal est obligatoire")
    private MainGoal mainGoal;
    
    @NotNull(message = "La préférence d'entraînement est obligatoire")
    private TrainingPreference trainingPreference;
    
    @NotNull(message = "L'équipement disponible est obligatoire")
    private Equipment equipment;

    public CreateTrainingInfoRequest() {}

    public CreateTrainingInfoRequest(Gender gender, Double weight, Double height, Double bodyFatPercentage,
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
        return "CreateTrainingInfoRequest{" +
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
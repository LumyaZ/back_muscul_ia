package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.enums.*;
import java.time.LocalDateTime;

/**
 * DTO for training information.
 * DTO pour les informations d'entraînement.
 */
public class TrainingInfoDto {
    
    private Long id;
    private Long userId;
    private Gender gender;
    private Double weight;
    private Double height;
    private Double bodyFatPercentage;
    private ExperienceLevel experienceLevel;
    private SessionFrequency sessionFrequency;
    private SessionDuration sessionDuration;
    private MainGoal mainGoal;
    private TrainingPreference trainingPreference;
    private Equipment equipment;
    private Double bmi;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public TrainingInfoDto() {}

    public TrainingInfoDto(TrainingInfo trainingInfo) {
        this.id = trainingInfo.getId();
        this.userId = trainingInfo.getUser().getId();
        this.gender = trainingInfo.getGender();
        this.weight = trainingInfo.getWeight();
        this.height = trainingInfo.getHeight();
        this.bodyFatPercentage = trainingInfo.getBodyFatPercentage();
        this.experienceLevel = trainingInfo.getExperienceLevel();
        this.sessionFrequency = trainingInfo.getSessionFrequency();
        this.sessionDuration = trainingInfo.getSessionDuration();
        this.mainGoal = trainingInfo.getMainGoal();
        this.trainingPreference = trainingInfo.getTrainingPreference();
        this.equipment = trainingInfo.getEquipment();
        this.bmi = trainingInfo.getBMI();
        this.createdAt = trainingInfo.getCreatedAt();
        this.updatedAt = trainingInfo.getUpdatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
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
    
    public Double getBmi() { return bmi; }
    public void setBmi(Double bmi) { this.bmi = bmi; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "TrainingInfoDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", gender=" + gender +
                ", weight=" + weight +
                ", height=" + height +
                ", bodyFatPercentage=" + bodyFatPercentage +
                ", experienceLevel=" + experienceLevel +
                ", sessionFrequency=" + sessionFrequency +
                ", sessionDuration=" + sessionDuration +
                ", mainGoal=" + mainGoal +
                ", trainingPreference=" + trainingPreference +
                ", equipment=" + equipment +
                ", bmi=" + bmi +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 
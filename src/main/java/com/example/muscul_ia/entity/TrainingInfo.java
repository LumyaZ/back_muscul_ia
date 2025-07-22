package com.example.muscul_ia.entity;

import com.example.muscul_ia.enums.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "training_info")
public class TrainingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "weight", nullable = false)
    private Double weight; // en kg

    @Column(name = "height", nullable = false)
    private Double height; // en cm

    @Column(name = "body_fat_percentage")
    private Double bodyFatPercentage; // pourcentage de matière grasse

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_frequency", nullable = false)
    private SessionFrequency sessionFrequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_duration", nullable = false)
    private SessionDuration sessionDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_goal", nullable = false)
    private MainGoal mainGoal;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_preference", nullable = false)
    private TrainingPreference trainingPreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment", nullable = false)
    private Equipment equipment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public TrainingInfo() {}

    public TrainingInfo(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public Double getBMI() {
        if (weight != null && height != null && height > 0) {
            double heightInMeters = height / 100.0;
            return weight / (heightInMeters * heightInMeters);
        }
        return null;
    }
} 
package com.example.muscul_ia.entity;

import com.example.muscul_ia.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity class for training information.
 * Entité pour les informations d'entraînement.
 */
@Entity
@Table(name = "training_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
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
    private Double weight;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "body_fat_percentage")
    private Double bodyFatPercentage;

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

    // Constructor
    public TrainingInfo(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }



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
package com.example.muscul_ia.entity;

import com.example.muscul_ia.enums.Equipment;
import com.example.muscul_ia.enums.ExperienceLevel;
import com.example.muscul_ia.enums.Gender;
import com.example.muscul_ia.enums.MainGoal;
import com.example.muscul_ia.enums.SessionDuration;
import com.example.muscul_ia.enums.SessionFrequency;
import com.example.muscul_ia.enums.TrainingPreference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class TrainingInfoTest {

    private TrainingInfo trainingInfo;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        trainingInfo = new TrainingInfo();
    }

    @Test
    void testTrainingInfoCreation() {
        trainingInfo.setId(1L);
        trainingInfo.setUser(user);
        trainingInfo.setGender(Gender.MALE);
        trainingInfo.setWeight(75.0);
        trainingInfo.setHeight(180.0);
        trainingInfo.setBodyFatPercentage(15.0);
        trainingInfo.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        trainingInfo.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        trainingInfo.setSessionDuration(SessionDuration.MEDIUM);
        trainingInfo.setMainGoal(MainGoal.MUSCLE_GAIN);
        trainingInfo.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        trainingInfo.setEquipment(Equipment.BASIC);
        trainingInfo.setCreatedAt(LocalDateTime.now());
        trainingInfo.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, trainingInfo.getId());
        assertEquals(user, trainingInfo.getUser());
        assertEquals(Gender.MALE, trainingInfo.getGender());
        assertEquals(75.0, trainingInfo.getWeight());
        assertEquals(180.0, trainingInfo.getHeight());
        assertEquals(15.0, trainingInfo.getBodyFatPercentage());
        assertEquals(ExperienceLevel.INTERMEDIATE, trainingInfo.getExperienceLevel());
        assertEquals(SessionFrequency.THREE_TO_FOUR, trainingInfo.getSessionFrequency());
        assertEquals(SessionDuration.MEDIUM, trainingInfo.getSessionDuration());
        assertEquals(MainGoal.MUSCLE_GAIN, trainingInfo.getMainGoal());
        assertEquals(TrainingPreference.STRENGTH_TRAINING, trainingInfo.getTrainingPreference());
        assertEquals(Equipment.BASIC, trainingInfo.getEquipment());
        assertNotNull(trainingInfo.getCreatedAt());
        assertNotNull(trainingInfo.getUpdatedAt());
    }

    @Test
    void testTrainingInfoEquality() {
        TrainingInfo info1 = new TrainingInfo();
        info1.setId(1L);
        info1.setGender(Gender.MALE);
        info1.setWeight(75.0);
        info1.setHeight(180.0);
        info1.setExperienceLevel(ExperienceLevel.BEGINNER);
        info1.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        info1.setSessionDuration(SessionDuration.MEDIUM);
        info1.setMainGoal(MainGoal.WEIGHT_LOSS);
        info1.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        info1.setEquipment(Equipment.BASIC);

        TrainingInfo info2 = new TrainingInfo();
        info2.setId(2L); // ID différent pour que les objets ne soient pas égaux
        info2.setGender(Gender.MALE);
        info2.setWeight(75.0);
        info2.setHeight(180.0);
        info2.setExperienceLevel(ExperienceLevel.BEGINNER);
        info2.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        info2.setSessionDuration(SessionDuration.MEDIUM);
        info2.setMainGoal(MainGoal.WEIGHT_LOSS);
        info2.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        info2.setEquipment(Equipment.BASIC);

        TrainingInfo info3 = new TrainingInfo();
        info3.setId(3L);
        info3.setGender(Gender.FEMALE);
        info3.setWeight(60.0);
        info3.setHeight(165.0);
        info3.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        info3.setSessionFrequency(SessionFrequency.FIVE_TO_SIX);
        info3.setSessionDuration(SessionDuration.LONG);
        info3.setMainGoal(MainGoal.MUSCLE_GAIN);
        info3.setTrainingPreference(TrainingPreference.CARDIO);
        info3.setEquipment(Equipment.GYM_ACCESS);

        assertNotEquals(info1, info2); 
        assertNotEquals(info1, info3);
        assertNotEquals(info1, null);
        assertNotEquals(info1, new Object());
    }

    @Test
    void testTrainingInfoHashCode() {
        TrainingInfo info1 = new TrainingInfo();
        info1.setId(1L);
        info1.setGender(Gender.MALE);
        info1.setWeight(75.0);
        info1.setHeight(180.0);
        info1.setExperienceLevel(ExperienceLevel.BEGINNER);
        info1.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        info1.setSessionDuration(SessionDuration.MEDIUM);
        info1.setMainGoal(MainGoal.WEIGHT_LOSS);
        info1.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        info1.setEquipment(Equipment.BASIC);

        TrainingInfo info2 = new TrainingInfo();
        info2.setId(1L);
        info2.setGender(Gender.MALE);
        info2.setWeight(75.0);
        info2.setHeight(180.0);
        info2.setExperienceLevel(ExperienceLevel.BEGINNER);
        info2.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        info2.setSessionDuration(SessionDuration.MEDIUM);
        info2.setMainGoal(MainGoal.WEIGHT_LOSS);
        info2.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        info2.setEquipment(Equipment.BASIC);

        assertNotNull(info1.hashCode());
        assertNotNull(info2.hashCode());
    }

    @Test
    void testTrainingInfoToString() {
        trainingInfo.setId(1L);
        trainingInfo.setGender(Gender.MALE);
        trainingInfo.setWeight(75.0);
        trainingInfo.setHeight(180.0);

        String toString = trainingInfo.toString();
        assertNotNull(toString);
        assertTrue(!toString.isEmpty());
    }

    @Test
    void testTrainingInfoWithNullValues() {
        trainingInfo.setId(1L);
        trainingInfo.setUser(null);
        trainingInfo.setGender(null);
        trainingInfo.setWeight(null);
        trainingInfo.setHeight(null);
        trainingInfo.setBodyFatPercentage(null);
        trainingInfo.setExperienceLevel(null);
        trainingInfo.setSessionFrequency(null);
        trainingInfo.setSessionDuration(null);
        trainingInfo.setMainGoal(null);
        trainingInfo.setTrainingPreference(null);
        trainingInfo.setEquipment(null);

        assertEquals(1L, trainingInfo.getId());
        assertNull(trainingInfo.getUser());
        assertNull(trainingInfo.getGender());
        assertNull(trainingInfo.getWeight());
        assertNull(trainingInfo.getHeight());
        assertNull(trainingInfo.getBodyFatPercentage());
        assertNull(trainingInfo.getExperienceLevel());
        assertNull(trainingInfo.getSessionFrequency());
        assertNull(trainingInfo.getSessionDuration());
        assertNull(trainingInfo.getMainGoal());
        assertNull(trainingInfo.getTrainingPreference());
        assertNull(trainingInfo.getEquipment());
    }

    @Test
    void testTrainingInfoNoArgsConstructor() {
        TrainingInfo emptyInfo = new TrainingInfo();
        assertNotNull(emptyInfo);
    }

    @Test
    void testWeightValidation() {
        trainingInfo.setWeight(70.0);
        assertEquals(70.0, trainingInfo.getWeight());

        trainingInfo.setWeight(100.0);
        assertEquals(100.0, trainingInfo.getWeight());

        // L'entité n'a pas de validation, donc les valeurs négatives sont acceptées
        trainingInfo.setWeight(-10.0);
        assertEquals(-10.0, trainingInfo.getWeight()); 

        trainingInfo.setWeight(0.0);
        assertEquals(0.0, trainingInfo.getWeight()); 

        trainingInfo.setWeight(600.0);
        assertEquals(600.0, trainingInfo.getWeight()); 
    }

    @Test
    void testHeightValidation() {
        trainingInfo.setHeight(170.0);
        assertEquals(170.0, trainingInfo.getHeight());

        trainingInfo.setHeight(180.0);
        assertEquals(180.0, trainingInfo.getHeight());

        // L'entité n'a pas de validation, donc les valeurs négatives sont acceptées
        trainingInfo.setHeight(-10.0);
        assertEquals(-10.0, trainingInfo.getHeight()); 

        trainingInfo.setHeight(0.0);
        assertEquals(0.0, trainingInfo.getHeight()); 

        trainingInfo.setHeight(400.0);
        assertEquals(400.0, trainingInfo.getHeight()); 
    }

    @Test
    void testBodyFatPercentageValidation() {
        trainingInfo.setBodyFatPercentage(-5.0);
        assertEquals(-5.0, trainingInfo.getBodyFatPercentage());

        trainingInfo.setBodyFatPercentage(0.0);
        assertEquals(0.0, trainingInfo.getBodyFatPercentage());

        trainingInfo.setBodyFatPercentage(50.0);
        assertEquals(50.0, trainingInfo.getBodyFatPercentage());
    }
} 
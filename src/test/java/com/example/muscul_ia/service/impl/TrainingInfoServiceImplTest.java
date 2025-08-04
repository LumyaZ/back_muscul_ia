package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.enums.Equipment;
import com.example.muscul_ia.enums.ExperienceLevel;
import com.example.muscul_ia.enums.Gender;
import com.example.muscul_ia.enums.MainGoal;
import com.example.muscul_ia.enums.SessionDuration;
import com.example.muscul_ia.enums.SessionFrequency;
import com.example.muscul_ia.enums.TrainingPreference;
import com.example.muscul_ia.repository.TrainingInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingInfoServiceImplTest {

    @Mock
    private TrainingInfoRepository trainingInfoRepository;

    @InjectMocks
    private TrainingInfoServiceImpl trainingInfoService;

    private User testUser;
    private TrainingInfo testTrainingInfo;
    private CreateTrainingInfoRequest createRequest;
    private UpdateTrainingInfoRequest updateRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testTrainingInfo = new TrainingInfo(testUser);
        testTrainingInfo.setId(1L);
        testTrainingInfo.setGender(Gender.MALE);
        testTrainingInfo.setWeight(75.0);
        testTrainingInfo.setHeight(180.0);
        testTrainingInfo.setBodyFatPercentage(15.0);
        testTrainingInfo.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        testTrainingInfo.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        testTrainingInfo.setSessionDuration(SessionDuration.MEDIUM);
        testTrainingInfo.setMainGoal(MainGoal.MUSCLE_GAIN);
        testTrainingInfo.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        testTrainingInfo.setEquipment(Equipment.GYM_ACCESS);
        testTrainingInfo.setCreatedAt(LocalDateTime.now());
        testTrainingInfo.setUpdatedAt(LocalDateTime.now());

        createRequest = new CreateTrainingInfoRequest();
        createRequest.setGender(Gender.MALE);
        createRequest.setWeight(75.0);
        createRequest.setHeight(180.0);
        createRequest.setBodyFatPercentage(15.0);
        createRequest.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        createRequest.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        createRequest.setSessionDuration(SessionDuration.MEDIUM);
        createRequest.setMainGoal(MainGoal.MUSCLE_GAIN);
        createRequest.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        createRequest.setEquipment(Equipment.GYM_ACCESS);

        updateRequest = new UpdateTrainingInfoRequest();
        updateRequest.setWeight(80.0);
        updateRequest.setHeight(182.0);
    }

    @Test
    void testCreateTrainingInfoSuccess() {
        // Arrange
        when(trainingInfoRepository.existsByUser(testUser)).thenReturn(false);
        when(trainingInfoRepository.save(any(TrainingInfo.class))).thenReturn(testTrainingInfo);

        // Act
        TrainingInfoDto result = trainingInfoService.createTrainingInfo(testUser, createRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testTrainingInfo.getId(), result.getId());
        assertEquals(testTrainingInfo.getGender(), result.getGender());
        assertEquals(testTrainingInfo.getWeight(), result.getWeight());
        assertEquals(testTrainingInfo.getHeight(), result.getHeight());

        verify(trainingInfoRepository).existsByUser(testUser);
        verify(trainingInfoRepository).save(any(TrainingInfo.class));
    }

    @Test
    void testCreateTrainingInfoAlreadyExists() {
        // Arrange
        when(trainingInfoRepository.existsByUser(testUser)).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingInfoService.createTrainingInfo(testUser, createRequest);
        });

        assertEquals("Training info already exists for this user", exception.getMessage());
        verify(trainingInfoRepository).existsByUser(testUser);
        verify(trainingInfoRepository, never()).save(any(TrainingInfo.class));
    }

    @Test
    void testGetTrainingInfoByUserSuccess() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.of(testTrainingInfo));

        // Act
        TrainingInfoDto result = trainingInfoService.getTrainingInfoByUser(testUser);

        // Assert
        assertNotNull(result);
        assertEquals(testTrainingInfo.getId(), result.getId());
        assertEquals(testTrainingInfo.getUser().getId(), result.getUserId());
        assertEquals(testTrainingInfo.getGender(), result.getGender());

        verify(trainingInfoRepository).findByUser(testUser);
    }

    @Test
    void testGetTrainingInfoByUserNotFound() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingInfoService.getTrainingInfoByUser(testUser);
        });

        assertEquals("Training info not found for user: " + testUser.getId(), exception.getMessage());
        verify(trainingInfoRepository).findByUser(testUser);
    }

    @Test
    void testGetTrainingInfoByUserIdSuccess() {
        // Arrange
        when(trainingInfoRepository.findByUserId(1L)).thenReturn(Optional.of(testTrainingInfo));

        // Act
        TrainingInfoDto result = trainingInfoService.getTrainingInfoByUserId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testTrainingInfo.getId(), result.getId());
        assertEquals(testTrainingInfo.getUser().getId(), result.getUserId());

        verify(trainingInfoRepository).findByUserId(1L);
    }

    @Test
    void testGetTrainingInfoByUserIdNotFound() {
        // Arrange
        when(trainingInfoRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingInfoService.getTrainingInfoByUserId(1L);
        });

        assertEquals("Training info not found for user ID: 1", exception.getMessage());
        verify(trainingInfoRepository).findByUserId(1L);
    }

    @Test
    void testUpdateTrainingInfoSuccess() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.of(testTrainingInfo));
        when(trainingInfoRepository.save(any(TrainingInfo.class))).thenReturn(testTrainingInfo);

        // Act
        TrainingInfoDto result = trainingInfoService.updateTrainingInfo(testUser, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testTrainingInfo.getId(), result.getId());

        verify(trainingInfoRepository).findByUser(testUser);
        verify(trainingInfoRepository).save(any(TrainingInfo.class));
    }

    @Test
    void testUpdateTrainingInfoNotFound() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingInfoService.updateTrainingInfo(testUser, updateRequest);
        });

        assertEquals("Training info not found for user: " + testUser.getId(), exception.getMessage());
        verify(trainingInfoRepository).findByUser(testUser);
        verify(trainingInfoRepository, never()).save(any(TrainingInfo.class));
    }

    @Test
    void testDeleteTrainingInfoSuccess() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.of(testTrainingInfo));
        doNothing().when(trainingInfoRepository).delete(testTrainingInfo);

        // Act
        trainingInfoService.deleteTrainingInfo(testUser);

        // Assert
        verify(trainingInfoRepository).findByUser(testUser);
        verify(trainingInfoRepository).delete(testTrainingInfo);
    }

    @Test
    void testDeleteTrainingInfoNotFound() {
        // Arrange
        when(trainingInfoRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingInfoService.deleteTrainingInfo(testUser);
        });

        assertEquals("Training info not found for user: " + testUser.getId(), exception.getMessage());
        verify(trainingInfoRepository).findByUser(testUser);
        verify(trainingInfoRepository, never()).delete(any(TrainingInfo.class));
    }

    @Test
    void testExistsByUser() {
        // Arrange
        when(trainingInfoRepository.existsByUser(testUser)).thenReturn(true);

        // Act
        boolean result = trainingInfoService.existsByUser(testUser);

        // Assert
        assertTrue(result);
        verify(trainingInfoRepository).existsByUser(testUser);
    }


} 
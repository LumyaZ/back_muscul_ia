package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.TrainingInfoRepository;
import com.example.muscul_ia.service.TrainingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TrainingInfoServiceImpl implements TrainingInfoService {

    @Autowired
    private TrainingInfoRepository trainingInfoRepository;

    @Override
    @Transactional
    public TrainingInfoDto createTrainingInfo(User user, CreateTrainingInfoRequest request) {
        System.out.println("=== TRAINING INFO SERVICE: CREATE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        System.out.println("Request: " + request);

        // Check if training info already exists for this user
        if (trainingInfoRepository.existsByUser(user)) {
            System.out.println("ERROR: Training info already exists for user " + user.getId());
            throw new RuntimeException("Training info already exists for this user");
        }

        // Create new training info
        TrainingInfo trainingInfo = new TrainingInfo(user);
        trainingInfo.setGender(request.getGender());
        trainingInfo.setWeight(request.getWeight());
        trainingInfo.setHeight(request.getHeight());
        trainingInfo.setBodyFatPercentage(request.getBodyFatPercentage());
        trainingInfo.setExperienceLevel(request.getExperienceLevel());
        trainingInfo.setSessionFrequency(request.getSessionFrequency());
        trainingInfo.setSessionDuration(request.getSessionDuration());
        trainingInfo.setMainGoal(request.getMainGoal());
        trainingInfo.setTrainingPreference(request.getTrainingPreference());
        trainingInfo.setEquipment(request.getEquipment());

        TrainingInfo savedTrainingInfo = trainingInfoRepository.save(trainingInfo);
        System.out.println("Training info created successfully: " + savedTrainingInfo.getId());

        return new TrainingInfoDto(savedTrainingInfo);
    }

    @Override
    public TrainingInfoDto getTrainingInfoByUser(User user) {
        System.out.println("=== TRAINING INFO SERVICE: GET BY USER ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());

        TrainingInfo trainingInfo = trainingInfoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Training info not found for user: " + user.getId()));

        System.out.println("Training info found: " + trainingInfo.getId());
        return new TrainingInfoDto(trainingInfo);
    }

    @Override
    public TrainingInfoDto getTrainingInfoByUserId(Long userId) {
        System.out.println("=== TRAINING INFO SERVICE: GET BY USER ID ===");
        System.out.println("User ID: " + userId);

        TrainingInfo trainingInfo = trainingInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Training info not found for user ID: " + userId));

        System.out.println("Training info found: " + trainingInfo.getId());
        return new TrainingInfoDto(trainingInfo);
    }

    @Override
    @Transactional
    public TrainingInfoDto updateTrainingInfo(User user, UpdateTrainingInfoRequest request) {
        System.out.println("=== TRAINING INFO SERVICE: UPDATE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        System.out.println("Request: " + request);

        TrainingInfo trainingInfo = trainingInfoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Training info not found for user: " + user.getId()));

        // Update fields if provided
        if (request.getGender() != null) {
            trainingInfo.setGender(request.getGender());
        }
        if (request.getWeight() != null) {
            trainingInfo.setWeight(request.getWeight());
        }
        if (request.getHeight() != null) {
            trainingInfo.setHeight(request.getHeight());
        }
        if (request.getBodyFatPercentage() != null) {
            trainingInfo.setBodyFatPercentage(request.getBodyFatPercentage());
        }
        if (request.getExperienceLevel() != null) {
            trainingInfo.setExperienceLevel(request.getExperienceLevel());
        }
        if (request.getSessionFrequency() != null) {
            trainingInfo.setSessionFrequency(request.getSessionFrequency());
        }
        if (request.getSessionDuration() != null) {
            trainingInfo.setSessionDuration(request.getSessionDuration());
        }
        if (request.getMainGoal() != null) {
            trainingInfo.setMainGoal(request.getMainGoal());
        }
        if (request.getTrainingPreference() != null) {
            trainingInfo.setTrainingPreference(request.getTrainingPreference());
        }
        if (request.getEquipment() != null) {
            trainingInfo.setEquipment(request.getEquipment());
        }

        trainingInfo.updateTimestamp();
        TrainingInfo savedTrainingInfo = trainingInfoRepository.save(trainingInfo);
        System.out.println("Training info updated successfully: " + savedTrainingInfo.getId());

        return new TrainingInfoDto(savedTrainingInfo);
    }

    @Override
    @Transactional
    public void deleteTrainingInfo(User user) {
        System.out.println("=== TRAINING INFO SERVICE: DELETE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());

        TrainingInfo trainingInfo = trainingInfoRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Training info not found for user: " + user.getId()));

        trainingInfoRepository.delete(trainingInfo);
        System.out.println("Training info deleted successfully: " + trainingInfo.getId());
    }

    @Override
    public boolean existsByUser(User user) {
        return trainingInfoRepository.existsByUser(user);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return trainingInfoRepository.existsByUserId(userId);
    }
} 
package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.TrainingInfoRepository;
import com.example.muscul_ia.service.TrainingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Training info service implementation for managing training information business logic.
 * Implémentation du service d'informations d'entraînement pour gérer la logique métier d'informations d'entraînement.
 */
@Service
public class TrainingInfoServiceImpl implements TrainingInfoService {
    
    @Autowired
    private TrainingInfoRepository trainingInfoRepository;
    
    @Override
    @Transactional
    public TrainingInfoDto createTrainingInfo(User user, CreateTrainingInfoRequest request) {
        if (trainingInfoRepository.existsByUser(user)) {
            throw new RuntimeException("Training info already exists for this user");
        }
        
        TrainingInfo trainingInfo = new TrainingInfo();
        trainingInfo.setUser(user);
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
        return new TrainingInfoDto(savedTrainingInfo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TrainingInfoDto getTrainingInfoByUser(User user) {
        Optional<TrainingInfo> trainingInfo = trainingInfoRepository.findByUser(user);
        if (trainingInfo.isPresent()) {
            return new TrainingInfoDto(trainingInfo.get());
        } else {
            throw new RuntimeException("Training info not found for user: " + user.getId());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public TrainingInfoDto getTrainingInfoByUserId(Long userId) {
        Optional<TrainingInfo> trainingInfo = trainingInfoRepository.findByUserId(userId);
        if (trainingInfo.isPresent()) {
            return new TrainingInfoDto(trainingInfo.get());
        } else {
            throw new RuntimeException("Training info not found for user ID: " + userId);
        }
    }
    
    @Override
    @Transactional
    public TrainingInfoDto updateTrainingInfo(User user, UpdateTrainingInfoRequest request) {
        Optional<TrainingInfo> existingTrainingInfo = trainingInfoRepository.findByUser(user);
        if (existingTrainingInfo.isEmpty()) {
            throw new RuntimeException("Training info not found for user: " + user.getId());
        }
        
        TrainingInfo trainingInfo = existingTrainingInfo.get();
        
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
        
        trainingInfo.setUpdatedAt(LocalDateTime.now());
        
        TrainingInfo savedTrainingInfo = trainingInfoRepository.save(trainingInfo);
        return new TrainingInfoDto(savedTrainingInfo);
    }
    
    @Override
    @Transactional
    public void deleteTrainingInfo(User user) {
        Optional<TrainingInfo> trainingInfo = trainingInfoRepository.findByUser(user);
        if (trainingInfo.isPresent()) {
            trainingInfoRepository.delete(trainingInfo.get());
        } else {
            throw new RuntimeException("Training info not found for user: " + user.getId());
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUser(User user) {
        return trainingInfoRepository.existsByUser(user);
    }
} 
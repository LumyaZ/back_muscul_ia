package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.UserTrainingProgram;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.UserTrainingProgramRepository;
import com.example.muscul_ia.service.UserTrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User training program service implementation for managing user-training program relationship business logic.
 * Implémentation du service de programmes d'entraînement utilisateur pour gérer la logique métier de relation utilisateur-programme.
 */
@Service
@Transactional
public class UserTrainingProgramServiceImpl implements UserTrainingProgramService {
    
    @Autowired
    private UserTrainingProgramRepository userTrainingProgramRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    
    @Override
    public UserTrainingProgramDto subscribeUserToProgram(Long userId, Long trainingProgramId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        TrainingProgram trainingProgram = trainingProgramRepository.findById(trainingProgramId)
                .orElseThrow(() -> new RuntimeException("Training program not found with id: " + trainingProgramId));
        
        if (userTrainingProgramRepository.findByUserIdAndTrainingProgramId(userId, trainingProgramId).isPresent()) {
            throw new RuntimeException("User is already subscribed to this program");
        }
        
        UserTrainingProgram userTrainingProgram = new UserTrainingProgram(user, trainingProgram);
        
        UserTrainingProgram saved = userTrainingProgramRepository.save(userTrainingProgram);
        return convertToDto(saved);
    }
    
    @Override
    public void unsubscribeUserFromProgram(Long userId, Long trainingProgramId) {
        userTrainingProgramRepository.findByUserIdAndTrainingProgramId(userId, trainingProgramId)
                .ifPresent(userTrainingProgramRepository::delete);
    }
    
    @Override
    public List<UserTrainingProgramDto> getUserPrograms(Long userId) {
        List<UserTrainingProgram> userPrograms = userTrainingProgramRepository.findByUserId(userId);
        return userPrograms.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserTrainingProgramDto> getProgramUsers(Long trainingProgramId) {
        List<UserTrainingProgram> programUsers = userTrainingProgramRepository.findByTrainingProgramId(trainingProgramId);
        return programUsers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public UserTrainingProgramDto getUserProgram(Long userId, Long trainingProgramId) {
        return userTrainingProgramRepository.findByUserIdAndTrainingProgramId(userId, trainingProgramId)
                .map(this::convertToDto)
                .orElse(null);
    }
    
    /**
     * Convert UserTrainingProgram entity to DTO.
     * Convertir l'entité UserTrainingProgram en DTO.
     */
    private UserTrainingProgramDto convertToDto(UserTrainingProgram userTrainingProgram) {
        UserDto userDto = new UserDto();
        userDto.setId(userTrainingProgram.getUser().getId());
        userDto.setEmail(userTrainingProgram.getUser().getEmail());
        
        TrainingProgramDto trainingProgramDto = new TrainingProgramDto();
        trainingProgramDto.setId(userTrainingProgram.getTrainingProgram().getId());
        trainingProgramDto.setName(userTrainingProgram.getTrainingProgram().getName());
        trainingProgramDto.setDescription(userTrainingProgram.getTrainingProgram().getDescription());
        trainingProgramDto.setDifficultyLevel(userTrainingProgram.getTrainingProgram().getDifficultyLevel());
        trainingProgramDto.setCategory(userTrainingProgram.getTrainingProgram().getCategory());
        trainingProgramDto.setTargetAudience(userTrainingProgram.getTrainingProgram().getTargetAudience());
        trainingProgramDto.setCreatedAt(userTrainingProgram.getTrainingProgram().getCreatedAt());
        trainingProgramDto.setUpdatedAt(userTrainingProgram.getTrainingProgram().getUpdatedAt());
        
        return new UserTrainingProgramDto(
            userTrainingProgram.getId(),
            userDto,
            trainingProgramDto
        );
    }
} 
package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Training program service implementation for managing training program business logic.
 * Implémentation du service de programmes d'entraînement pour gérer la logique métier de programmes d'entraînement.
 */
@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {
    
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    
    @Autowired
    private ProgramExerciseRepository programExerciseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        TrainingProgram program = new TrainingProgram();
        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setDifficultyLevel(request.getDifficultyLevel());
        program.setCategory(request.getCategory());
        program.setTargetAudience(request.getTargetAudience());
        program.setCreatedByUser(userOpt.get());
        
        TrainingProgram savedProgram = trainingProgramRepository.save(program);
        
        return convertToDto(savedProgram);
    }
    
    @Override
    public List<TrainingProgramDto> getAllActivePrograms() {
        List<TrainingProgram> programs = trainingProgramRepository.findAll();
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getAllPublicActivePrograms() {
        List<TrainingProgram> programs = trainingProgramRepository.findAll();
        return convertToDtoList(programs);
    }
    
    @Override
    public Optional<TrainingProgramDto> getProgramById(Long id) {
        Optional<TrainingProgram> program = trainingProgramRepository.findById(id);
        return program.map(this::convertToDto);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                    .filter(p -> p.getCreatedByUser().getId().equals(userId))
                    .collect(Collectors.toList());
            return convertToDtoList(programs);
        }
        return List.of();
    }
    
    @Override
    @Transactional
    public TrainingProgramDto updateProgram(Long id, CreateTrainingProgramRequest request, Long userId) {
        Optional<TrainingProgram> programOpt = trainingProgramRepository.findById(id);
        if (programOpt.isPresent()) {
            TrainingProgram program = programOpt.get();
            
            if (!program.getCreatedByUser().getId().equals(userId)) {
                throw new RuntimeException("User is not authorized to update this program");
            }
            
            program.setName(request.getName());
            program.setDescription(request.getDescription());
            program.setDifficultyLevel(request.getDifficultyLevel());
            program.setCategory(request.getCategory());
            program.setTargetAudience(request.getTargetAudience());
            
            TrainingProgram updatedProgram = trainingProgramRepository.save(program);
            return convertToDto(updatedProgram);
        }
        throw new RuntimeException("Program not found with id: " + id);
    }
    
    @Override
    @Transactional
    public void deleteProgram(Long id, Long userId) {
        Optional<TrainingProgram> programOpt = trainingProgramRepository.findById(id);
        if (programOpt.isPresent()) {
            TrainingProgram program = programOpt.get();
            
            if (!program.getCreatedByUser().getId().equals(userId)) {
                throw new RuntimeException("User is not authorized to delete this program");
            }
            
            programExerciseRepository.deleteByTrainingProgram(program);
            trainingProgramRepository.delete(program);
        } else {
            throw new RuntimeException("Program not found with id: " + id);
        }
    }
    
    @Override
    public List<TrainingProgramDto> searchProgramsByName(String name) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchPublicProgramsByName(String name) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByDifficultyLevel(String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getDifficultyLevel().equals(difficultyLevel))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByDifficultyLevel(String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getDifficultyLevel().equals(difficultyLevel))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByCategory(String category) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByCategory(String category) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByTargetAudience(String targetAudience) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getTargetAudience().equals(targetAudience))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByTargetAudience(String targetAudience) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getTargetAudience().equals(targetAudience))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchProgramsByDescription(String description) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchPublicProgramsByDescription(String description) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByCategoryAndDifficulty(String category, String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category) && p.getDifficultyLevel().equals(difficultyLevel))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByCategoryAndDifficulty(String category, String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findAll().stream()
                .filter(p -> p.getCategory().equals(category) && p.getDifficultyLevel().equals(difficultyLevel))
                .collect(Collectors.toList());
        return convertToDtoList(programs);
    }
    
    @Override
    public TrainingProgramDto convertToDto(TrainingProgram program) {
        TrainingProgramDto dto = new TrainingProgramDto();
        dto.setId(program.getId());
        dto.setName(program.getName());
        dto.setDescription(program.getDescription());
        dto.setDifficultyLevel(program.getDifficultyLevel());
        dto.setCategory(program.getCategory());
        dto.setTargetAudience(program.getTargetAudience());
        dto.setCreatedByUserId(program.getCreatedByUser().getId());
        dto.setCreatedAt(program.getCreatedAt());
        dto.setUpdatedAt(program.getUpdatedAt());
        return dto;
    }
    
    @Override
    public List<TrainingProgramDto> convertToDtoList(List<TrainingProgram> programs) {
        return programs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
} 
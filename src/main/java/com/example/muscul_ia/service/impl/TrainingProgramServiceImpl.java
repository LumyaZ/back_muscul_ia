package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.ExerciseRepository;
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

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {
    
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    
    @Autowired
    private ProgramExerciseRepository programExerciseRepository;
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional
    public TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request, Long userId) {
        // Récupérer l'utilisateur
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        // Créer le programme
        TrainingProgram program = new TrainingProgram();
        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setDifficultyLevel(request.getDifficultyLevel());
        program.setDurationWeeks(request.getDurationWeeks());
        program.setSessionsPerWeek(request.getSessionsPerWeek());
        program.setEstimatedDurationMinutes(request.getEstimatedDurationMinutes());
        program.setCategory(request.getCategory());
        program.setTargetAudience(request.getTargetAudience());
        program.setEquipmentRequired(request.getEquipmentRequired());
        program.setImageUrl(request.getImageUrl());
        program.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
        program.setIsActive(true);
        program.setCreatedByUser(userOpt.get());
        
        TrainingProgram savedProgram = trainingProgramRepository.save(program);
        
        // Ajouter les exercices au programme
        if (request.getExercises() != null && !request.getExercises().isEmpty()) {
            for (CreateTrainingProgramRequest.ProgramExerciseRequest exerciseRequest : request.getExercises()) {
                Optional<Exercise> exerciseOpt = exerciseRepository.findById(exerciseRequest.getExerciseId());
                if (exerciseOpt.isPresent()) {
                    ProgramExercise programExercise = new ProgramExercise();
                    programExercise.setTrainingProgram(savedProgram);
                    programExercise.setExercise(exerciseOpt.get());
                    programExercise.setOrderInProgram(exerciseRequest.getOrderInProgram());
                    programExercise.setSetsCount(exerciseRequest.getSetsCount());
                    programExercise.setRepsCount(exerciseRequest.getRepsCount());
                    programExercise.setDurationSeconds(exerciseRequest.getDurationSeconds());
                    programExercise.setRestDurationSeconds(exerciseRequest.getRestDurationSeconds());
                    programExercise.setWeightKg(exerciseRequest.getWeightKg());
                    programExercise.setDistanceMeters(exerciseRequest.getDistanceMeters());
                    programExercise.setNotes(exerciseRequest.getNotes());
                    programExercise.setIsOptional(exerciseRequest.getIsOptional() != null ? exerciseRequest.getIsOptional() : false);
                    
                    programExerciseRepository.save(programExercise);
                }
            }
        }
        
        return convertToDto(savedProgram);
    }
    
    @Override
    public List<TrainingProgramDto> getAllActivePrograms() {
        List<TrainingProgram> programs = trainingProgramRepository.findByIsActiveTrue();
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getAllPublicActivePrograms() {
        List<TrainingProgram> programs = trainingProgramRepository.findByIsPublicTrueAndIsActiveTrue();
        return convertToDtoList(programs);
    }
    
    @Override
    public Optional<TrainingProgramDto> getProgramById(Long id) {
        Optional<TrainingProgram> program = trainingProgramRepository.findById(id);
        return program.map(this::convertToDto);
    }
    
    @Override
    public Optional<TrainingProgram> getProgramEntityById(Long id) {
        return trainingProgramRepository.findById(id);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            List<TrainingProgram> programs = trainingProgramRepository.findByCreatedByUserAndIsActiveTrue(userOpt.get());
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
            
            // Vérifier que l'utilisateur est le propriétaire du programme
            if (!program.getCreatedByUser().getId().equals(userId)) {
                throw new RuntimeException("User is not authorized to update this program");
            }
            
            // Mettre à jour les propriétés du programme
            program.setName(request.getName());
            program.setDescription(request.getDescription());
            program.setDifficultyLevel(request.getDifficultyLevel());
            program.setDurationWeeks(request.getDurationWeeks());
            program.setSessionsPerWeek(request.getSessionsPerWeek());
            program.setEstimatedDurationMinutes(request.getEstimatedDurationMinutes());
            program.setCategory(request.getCategory());
            program.setTargetAudience(request.getTargetAudience());
            program.setEquipmentRequired(request.getEquipmentRequired());
            program.setImageUrl(request.getImageUrl());
            program.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : program.getIsPublic());
            
            TrainingProgram updatedProgram = trainingProgramRepository.save(program);
            
            // Supprimer les anciens exercices du programme
            programExerciseRepository.deleteByTrainingProgram(program);
            
            // Ajouter les nouveaux exercices
            if (request.getExercises() != null && !request.getExercises().isEmpty()) {
                for (CreateTrainingProgramRequest.ProgramExerciseRequest exerciseRequest : request.getExercises()) {
                    Optional<Exercise> exerciseOpt = exerciseRepository.findById(exerciseRequest.getExerciseId());
                    if (exerciseOpt.isPresent()) {
                        ProgramExercise programExercise = new ProgramExercise();
                        programExercise.setTrainingProgram(updatedProgram);
                        programExercise.setExercise(exerciseOpt.get());
                        programExercise.setOrderInProgram(exerciseRequest.getOrderInProgram());
                        programExercise.setSetsCount(exerciseRequest.getSetsCount());
                        programExercise.setRepsCount(exerciseRequest.getRepsCount());
                        programExercise.setDurationSeconds(exerciseRequest.getDurationSeconds());
                        programExercise.setRestDurationSeconds(exerciseRequest.getRestDurationSeconds());
                        programExercise.setWeightKg(exerciseRequest.getWeightKg());
                        programExercise.setDistanceMeters(exerciseRequest.getDistanceMeters());
                        programExercise.setNotes(exerciseRequest.getNotes());
                        programExercise.setIsOptional(exerciseRequest.getIsOptional() != null ? exerciseRequest.getIsOptional() : false);
                        
                        programExerciseRepository.save(programExercise);
                    }
                }
            }
            
            return convertToDto(updatedProgram);
        }
        throw new RuntimeException("Training program not found with id: " + id);
    }
    
    @Override
    @Transactional
    public void deleteProgram(Long id, Long userId) {
        Optional<TrainingProgram> programOpt = trainingProgramRepository.findById(id);
        if (programOpt.isPresent()) {
            TrainingProgram program = programOpt.get();
            
            // Vérifier que l'utilisateur est le propriétaire du programme
            if (!program.getCreatedByUser().getId().equals(userId)) {
                throw new RuntimeException("User is not authorized to delete this program");
            }
            
            // Désactiver le programme
            program.setIsActive(false);
            trainingProgramRepository.save(program);
            
            // Supprimer les exercices du programme
            programExerciseRepository.deleteByTrainingProgram(program);
        } else {
            throw new RuntimeException("Training program not found with id: " + id);
        }
    }
    
    @Override
    public List<TrainingProgramDto> searchProgramsByName(String name) {
        List<TrainingProgram> programs = trainingProgramRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchPublicProgramsByName(String name) {
        List<TrainingProgram> programs = trainingProgramRepository.findByNameContainingIgnoreCaseAndIsPublicTrueAndIsActiveTrue(name);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByDifficultyLevel(String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDifficultyLevelAndIsActiveTrue(difficultyLevel);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByDifficultyLevel(String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDifficultyLevelAndIsPublicTrueAndIsActiveTrue(difficultyLevel);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByCategory(String category) {
        List<TrainingProgram> programs = trainingProgramRepository.findByCategoryAndIsActiveTrue(category);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByCategory(String category) {
        List<TrainingProgram> programs = trainingProgramRepository.findByCategoryAndIsPublicTrueAndIsActiveTrue(category);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByTargetAudience(String targetAudience) {
        List<TrainingProgram> programs = trainingProgramRepository.findByTargetAudienceAndIsActiveTrue(targetAudience);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByTargetAudience(String targetAudience) {
        List<TrainingProgram> programs = trainingProgramRepository.findByTargetAudienceAndIsPublicTrueAndIsActiveTrue(targetAudience);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByDuration(Integer durationWeeks) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDurationWeeksAndIsActiveTrue(durationWeeks);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByDuration(Integer durationWeeks) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDurationWeeksAndIsPublicTrueAndIsActiveTrue(durationWeeks);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsBySessionsPerWeek(Integer sessionsPerWeek) {
        List<TrainingProgram> programs = trainingProgramRepository.findBySessionsPerWeekAndIsActiveTrue(sessionsPerWeek);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsBySessionsPerWeek(Integer sessionsPerWeek) {
        List<TrainingProgram> programs = trainingProgramRepository.findBySessionsPerWeekAndIsPublicTrueAndIsActiveTrue(sessionsPerWeek);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchProgramsByDescription(String description) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDescriptionContainingIgnoreCaseAndIsActiveTrue(description);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> searchPublicProgramsByDescription(String description) {
        List<TrainingProgram> programs = trainingProgramRepository.findByDescriptionContainingIgnoreCaseAndIsPublicTrueAndIsActiveTrue(description);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getProgramsByCategoryAndDifficulty(String category, String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findByCategoryAndDifficultyLevelAndIsActiveTrue(category, difficultyLevel);
        return convertToDtoList(programs);
    }
    
    @Override
    public List<TrainingProgramDto> getPublicProgramsByCategoryAndDifficulty(String category, String difficultyLevel) {
        List<TrainingProgram> programs = trainingProgramRepository.findByCategoryAndDifficultyLevelAndIsPublicTrueAndIsActiveTrue(category, difficultyLevel);
        return convertToDtoList(programs);
    }
    
    @Override
    public TrainingProgramDto convertToDto(TrainingProgram program) {
        TrainingProgramDto dto = new TrainingProgramDto();
        dto.setId(program.getId());
        dto.setName(program.getName());
        dto.setDescription(program.getDescription());
        dto.setDifficultyLevel(program.getDifficultyLevel());
        dto.setDurationWeeks(program.getDurationWeeks());
        dto.setSessionsPerWeek(program.getSessionsPerWeek());
        dto.setEstimatedDurationMinutes(program.getEstimatedDurationMinutes());
        dto.setCategory(program.getCategory());
        dto.setTargetAudience(program.getTargetAudience());
        dto.setEquipmentRequired(program.getEquipmentRequired());
        dto.setImageUrl(program.getImageUrl());
        dto.setIsPublic(program.getIsPublic());
        dto.setIsActive(program.getIsActive());
        dto.setCreatedAt(program.getCreatedAt());
        dto.setUpdatedAt(program.getUpdatedAt());
        if (program.getCreatedByUser() != null) {
            dto.setCreatedByUserId(program.getCreatedByUser().getId());
        }
        return dto;
    }
    
    @Override
    public List<TrainingProgramDto> convertToDtoList(List<TrainingProgram> programs) {
        return programs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
} 
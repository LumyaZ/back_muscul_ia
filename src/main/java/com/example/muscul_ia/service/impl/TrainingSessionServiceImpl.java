package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingSessionRequest;
import com.example.muscul_ia.dto.TrainingSessionDto;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.TrainingSession;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.TrainingSessionRepository;
import com.example.muscul_ia.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of TrainingSessionService.
 * Implémentation du service TrainingSessionService.
 * 
 * This service provides business logic for managing training sessions including
 * CRUD operations, searching, filtering, and data validation.
 * 
 * Ce service fournit la logique métier pour gérer les sessions d'entraînement
 * incluant les opérations CRUD, la recherche, le filtrage et la validation des données.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {
    
    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    
    @Autowired
    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository,
                                   TrainingProgramRepository trainingProgramRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }
    
    @Override
    @Transactional
    public TrainingSessionDto createTrainingSession(User user, CreateTrainingSessionRequest request) {
        System.out.println("=== TRAINING SESSION SERVICE: CREATE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        System.out.println("Request: " + request);
        
        // Create new training session
        TrainingSession trainingSession = new TrainingSession(user, request.getSessionDate());
        trainingSession.setName(request.getName());
        trainingSession.setDescription(request.getDescription());
        trainingSession.setDurationMinutes(request.getDurationMinutes());
        trainingSession.setSessionType(request.getSessionType());
        
        // Set training program if provided
        if (request.getTrainingProgramId() != null) {
            Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(request.getTrainingProgramId());
            if (trainingProgram.isPresent()) {
                trainingSession.setTrainingProgram(trainingProgram.get());
            } else {
                System.out.println("WARNING: Training program with ID " + request.getTrainingProgramId() + " not found");
            }
        }
        
        TrainingSession savedTrainingSession = trainingSessionRepository.save(trainingSession);
        System.out.println("Training session created successfully: " + savedTrainingSession.getId());
        
        return new TrainingSessionDto(savedTrainingSession);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingSessionDto> getTrainingSessionById(Long sessionId) {
        return trainingSessionRepository.findById(sessionId)
                .map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUser(User user) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdWithTrainingProgram(user.getId());
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TrainingSessionDto> getTrainingSessionsByUser(User user, Pageable pageable) {
        Page<TrainingSession> sessions = trainingSessionRepository.findByUserIdWithTrainingProgram(user.getId(), pageable);
        return sessions.map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserId(Long userId) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdWithTrainingProgram(userId);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TrainingSessionDto> getTrainingSessionsByUserId(Long userId, Pageable pageable) {
        Page<TrainingSession> sessions = trainingSessionRepository.findByUserIdWithTrainingProgram(userId, pageable);
        return sessions.map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdAndDateRangeWithTrainingProgram(
                user.getId(), startDate, endDate);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdAndDateRangeWithTrainingProgram(
                userId, startDate, endDate);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndType(User user, String sessionType) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserAndSessionType(user, sessionType);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserIdAndType(Long userId, String sessionType) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdAndSessionType(userId, sessionType);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndTrainingProgram(User user, Long trainingProgramId) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserAndTrainingProgramId(user, trainingProgramId);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserIdAndTrainingProgram(Long userId, Long trainingProgramId) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdAndTrainingProgramId(userId, trainingProgramId);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> searchTrainingSessionsByUserAndName(User user, String name) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserAndNameContainingIgnoreCase(user, name);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> searchTrainingSessionsByUserIdAndName(Long userId, String name) {
        List<TrainingSession> sessions = trainingSessionRepository.findByUserIdAndNameContainingIgnoreCase(userId, name);
        return sessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingSessionDto> getMostRecentTrainingSessionByUser(User user) {
        return trainingSessionRepository.findFirstByUserOrderBySessionDateDesc(user)
                .map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingSessionDto> getMostRecentTrainingSessionByUserId(Long userId) {
        return trainingSessionRepository.findFirstByUserIdOrderBySessionDateDesc(userId)
                .map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countTrainingSessionsByUser(User user) {
        return trainingSessionRepository.countByUser(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countTrainingSessionsByUserId(Long userId) {
        return trainingSessionRepository.countByUserId(userId);
    }
    
    @Override
    @Transactional
    public TrainingSessionDto updateTrainingSession(Long sessionId, CreateTrainingSessionRequest request) {
        System.out.println("=== TRAINING SESSION SERVICE: UPDATE ===");
        System.out.println("Session ID: " + sessionId);
        System.out.println("Request: " + request);
        
        TrainingSession trainingSession = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Training session not found with ID: " + sessionId));
        
        // Update fields
        trainingSession.setName(request.getName());
        trainingSession.setDescription(request.getDescription());
        trainingSession.setSessionDate(request.getSessionDate());
        trainingSession.setDurationMinutes(request.getDurationMinutes());
        trainingSession.setSessionType(request.getSessionType());
        
        // Update training program if provided
        if (request.getTrainingProgramId() != null) {
            Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(request.getTrainingProgramId());
            if (trainingProgram.isPresent()) {
                trainingSession.setTrainingProgram(trainingProgram.get());
            } else {
                System.out.println("WARNING: Training program with ID " + request.getTrainingProgramId() + " not found");
                trainingSession.setTrainingProgram(null);
            }
        } else {
            trainingSession.setTrainingProgram(null);
        }
        
        TrainingSession savedTrainingSession = trainingSessionRepository.save(trainingSession);
        System.out.println("Training session updated successfully: " + savedTrainingSession.getId());
        
        return new TrainingSessionDto(savedTrainingSession);
    }
    
    @Override
    @Transactional
    public void deleteTrainingSession(Long sessionId) {
        System.out.println("=== TRAINING SESSION SERVICE: DELETE ===");
        System.out.println("Session ID: " + sessionId);
        
        if (!trainingSessionRepository.existsById(sessionId)) {
            throw new RuntimeException("Training session not found with ID: " + sessionId);
        }
        
        trainingSessionRepository.deleteById(sessionId);
        System.out.println("Training session deleted successfully: " + sessionId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsTrainingSession(Long sessionId) {
        return trainingSessionRepository.existsById(sessionId);
    }
} 
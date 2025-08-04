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
 * Training session service implementation for managing training session business logic.
 * Implémentation du service de sessions d'entraînement pour gérer la logique métier de sessions d'entraînement.
 */
@Service
public class TrainingSessionServiceImpl implements TrainingSessionService {
    
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    
    @Override
    @Transactional
    public TrainingSessionDto createTrainingSession(User user, CreateTrainingSessionRequest request) {
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setUser(user);
        trainingSession.setName(request.getName());
        trainingSession.setDescription(request.getDescription());
        trainingSession.setSessionDate(request.getSessionDate());
        trainingSession.setDurationMinutes(request.getDurationMinutes());
        trainingSession.setSessionType(request.getSessionType());
        
        if (request.getTrainingProgramId() != null) {
            Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(request.getTrainingProgramId());
            if (trainingProgram.isPresent()) {
                trainingSession.setTrainingProgram(trainingProgram.get());
            } else {
                trainingSession.setTrainingProgram(null);
            }
        } else {
            trainingSession.setTrainingProgram(null);
        }
        
        TrainingSession savedTrainingSession = trainingSessionRepository.save(trainingSession);
        return new TrainingSessionDto(savedTrainingSession);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingSessionDto> getTrainingSessionById(Long sessionId) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(sessionId);
        return trainingSession.map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUser(User user) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdWithTrainingProgram(user.getId());
        return trainingSessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TrainingSessionDto> getTrainingSessionsByUserId(Long userId, Pageable pageable) {
        Page<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdWithTrainingProgram(userId, pageable);
        return trainingSessions.map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdAndDateRangeWithTrainingProgram(
                user.getId(), startDate, endDate);
        return trainingSessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndType(User user, String sessionType) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdAndSessionType(user.getId(), sessionType);
        return trainingSessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> getTrainingSessionsByUserAndTrainingProgram(User user, Long trainingProgramId) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdAndTrainingProgramId(user.getId(), trainingProgramId);
        return trainingSessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDto> searchTrainingSessionsByUserAndName(User user, String name) {
        List<TrainingSession> trainingSessions = trainingSessionRepository.findByUserIdAndNameContainingIgnoreCase(user.getId(), name);
        return trainingSessions.stream()
                .map(TrainingSessionDto::new)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingSessionDto> getMostRecentTrainingSessionByUser(User user) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findFirstByUserIdOrderBySessionDateDesc(user.getId());
        return trainingSession.map(TrainingSessionDto::new);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countTrainingSessionsByUser(User user) {
        return trainingSessionRepository.countByUserId(user.getId());
    }
    
    @Override
    @Transactional
    public TrainingSessionDto updateTrainingSession(Long sessionId, CreateTrainingSessionRequest request) {
        Optional<TrainingSession> trainingSessionOpt = trainingSessionRepository.findById(sessionId);
        if (trainingSessionOpt.isPresent()) {
            TrainingSession trainingSession = trainingSessionOpt.get();
            
            trainingSession.setName(request.getName());
            trainingSession.setDescription(request.getDescription());
            trainingSession.setSessionDate(request.getSessionDate());
            trainingSession.setDurationMinutes(request.getDurationMinutes());
            trainingSession.setSessionType(request.getSessionType());
            
            if (request.getTrainingProgramId() != null) {
                Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(request.getTrainingProgramId());
                if (trainingProgram.isPresent()) {
                    trainingSession.setTrainingProgram(trainingProgram.get());
                } else {
                    trainingSession.setTrainingProgram(null);
                }
            } else {
                trainingSession.setTrainingProgram(null);
            }
            
            TrainingSession updatedTrainingSession = trainingSessionRepository.save(trainingSession);
            return new TrainingSessionDto(updatedTrainingSession);
        }
        throw new RuntimeException("Training session not found with id: " + sessionId);
    }
    
    @Override
    @Transactional
    public void deleteTrainingSession(Long sessionId) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(sessionId);
        if (trainingSession.isPresent()) {
            trainingSessionRepository.delete(trainingSession.get());
        } else {
            throw new RuntimeException("Training session not found with id: " + sessionId);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsTrainingSession(Long sessionId) {
        return trainingSessionRepository.existsById(sessionId);
    }
} 
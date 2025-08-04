package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import java.util.List;

/**
 * User training program service for managing user-training program relationship business logic.
 * Service de programmes d'entraînement utilisateur pour gérer la logique métier de relation utilisateur-programme.
 */
public interface UserTrainingProgramService {
    
    /**
     * Subscribe a user to a training program.
     * Abonner un utilisateur à un programme d'entraînement.
     */
    UserTrainingProgramDto subscribeUserToProgram(Long userId, Long trainingProgramId);
    
    /**
     * Unsubscribe a user from a training program.
     * Désabonner un utilisateur d'un programme d'entraînement.
     */
    void unsubscribeUserFromProgram(Long userId, Long trainingProgramId);
    
    /**
     * Get all training programs that a user is subscribed to.
     * Récupérer tous les programmes d'entraînement auxquels un utilisateur est abonné.
     */
    List<UserTrainingProgramDto> getUserPrograms(Long userId);
    
    /**
     * Get all users subscribed to a specific training program.
     * Récupérer tous les utilisateurs abonnés à un programme d'entraînement spécifique.
     */
    List<UserTrainingProgramDto> getProgramUsers(Long trainingProgramId);
    
    /**
     * Check if a user is subscribed to a specific training program.
     * Vérifier si un utilisateur est abonné à un programme d'entraînement spécifique.
     */
    UserTrainingProgramDto getUserProgram(Long userId, Long trainingProgramId);
} 
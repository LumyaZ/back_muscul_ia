package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import java.util.List;

/**
 * Service interface for managing user-training program relationships.
 * Interface de service pour gérer les relations utilisateur-programme d'entraînement.
 * 
 * This service provides business logic for managing the simple relationship between
 * users and training programs.
 * 
 * Ce service fournit la logique métier pour gérer la relation simple entre les
 * utilisateurs et les programmes d'entraînement.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface UserTrainingProgramService {
    
    /**
     * Subscribe a user to a training program.
     * Abonner un utilisateur à un programme d'entraînement.
     * 
     * @param userId - ID of the user
     * @param trainingProgramId - ID of the training program
     * @return UserTrainingProgramDto - Created relationship
     */
    UserTrainingProgramDto subscribeUserToProgram(Long userId, Long trainingProgramId);
    
    /**
     * Unsubscribe a user from a training program.
     * Désabonner un utilisateur d'un programme d'entraînement.
     * 
     * @param userId - ID of the user
     * @param trainingProgramId - ID of the training program
     */
    void unsubscribeUserFromProgram(Long userId, Long trainingProgramId);
    
    /**
     * Get all training programs that a user is subscribed to.
     * Récupérer tous les programmes d'entraînement auxquels un utilisateur est abonné.
     * 
     * @param userId - ID of the user
     * @return List of UserTrainingProgramDto
     */
    List<UserTrainingProgramDto> getUserPrograms(Long userId);
    
    /**
     * Get all users subscribed to a specific training program.
     * Récupérer tous les utilisateurs abonnés à un programme d'entraînement spécifique.
     * 
     * @param trainingProgramId - ID of the training program
     * @return List of UserTrainingProgramDto
     */
    List<UserTrainingProgramDto> getProgramUsers(Long trainingProgramId);
    
    /**
     * Check if a user is subscribed to a specific training program.
     * Vérifier si un utilisateur est abonné à un programme d'entraînement spécifique.
     * 
     * @param userId - ID of the user
     * @param trainingProgramId - ID of the training program
     * @return UserTrainingProgramDto if subscribed, null otherwise
     */
    UserTrainingProgramDto getUserProgram(Long userId, Long trainingProgramId);
} 
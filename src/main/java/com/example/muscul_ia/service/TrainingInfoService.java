package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.entity.User;

/**
 * Training info service for managing training information business logic.
 * Service d'informations d'entraînement pour gérer la logique métier d'informations d'entraînement.
 */
public interface TrainingInfoService {
    
    /**
     * Create training info for a user.
     * Créer les informations d'entraînement pour un utilisateur.
     */
    TrainingInfoDto createTrainingInfo(User user, CreateTrainingInfoRequest request);
    
    /**
     * Get training info by user.
     * Obtenir les informations d'entraînement par utilisateur.
     */
    TrainingInfoDto getTrainingInfoByUser(User user);
    
    /**
     * Get training info by user ID.
     * Obtenir les informations d'entraînement par ID utilisateur.
     */
    TrainingInfoDto getTrainingInfoByUserId(Long userId);
    
    /**
     * Update training info for a user.
     * Mettre à jour les informations d'entraînement pour un utilisateur.
     */
    TrainingInfoDto updateTrainingInfo(User user, UpdateTrainingInfoRequest request);
    
    /**
     * Delete training info for a user.
     * Supprimer les informations d'entraînement pour un utilisateur.
     */
    void deleteTrainingInfo(User user);
    
    /**
     * Check if training info exists for user.
     * Vérifier si les informations d'entraînement existent pour l'utilisateur.
     */
    boolean existsByUser(User user);
} 
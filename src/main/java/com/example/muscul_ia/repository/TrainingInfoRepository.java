package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Training info repository for managing training information data operations.
 * Repository d'informations d'entraînement pour gérer les opérations de données d'informations d'entraînement.
 */
@Repository
public interface TrainingInfoRepository extends JpaRepository<TrainingInfo, Long> {
    
    /**
     * Find training info by user.
     * Trouver les informations d'entraînement par utilisateur.
     */
    Optional<TrainingInfo> findByUser(User user);
    
    /**
     * Find training info by user ID.
     * Trouver les informations d'entraînement par ID utilisateur.
     */
    Optional<TrainingInfo> findByUserId(Long userId);
    
    /**
     * Check if training info exists by user.
     * Vérifier si les informations d'entraînement existent par utilisateur.
     */
    boolean existsByUser(User user);
} 
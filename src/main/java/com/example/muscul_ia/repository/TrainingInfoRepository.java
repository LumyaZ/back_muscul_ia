package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingInfoRepository extends JpaRepository<TrainingInfo, Long> {
    
    /**
     * Find training info by user
     * Trouver les informations d'entraînement par utilisateur
     */
    Optional<TrainingInfo> findByUser(User user);
    
    /**
     * Find training info by user ID
     * Trouver les informations d'entraînement par ID utilisateur
     */
    Optional<TrainingInfo> findByUserId(Long userId);
    
    /**
     * Check if training info exists for user
     * Vérifier si les informations d'entraînement existent pour l'utilisateur
     */
    boolean existsByUser(User user);
    
    /**
     * Check if training info exists for user ID
     * Vérifier si les informations d'entraînement existent pour l'ID utilisateur
     */
    boolean existsByUserId(Long userId);
} 
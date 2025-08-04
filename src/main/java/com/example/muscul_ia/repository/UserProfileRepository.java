package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User profile repository for managing user profile data operations.
 * Repository de profil utilisateur pour gérer les opérations de données de profil utilisateur.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Find user profile by user.
     * Trouver un profil utilisateur par utilisateur.
     */
    Optional<UserProfile> findByUser(User user);

    /**
     * Check if user profile exists by user.
     * Vérifier si un profil utilisateur existe par utilisateur.
     */
    boolean existsByUser(User user);
} 
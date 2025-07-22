package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for UserProfile entity.
 * Repository pour l'entité UserProfile.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /** 
     * Find user profile by user.
     * Trouve le profil utilisateur par utilisateur.
     */
    Optional<UserProfile> findByUser(User user);

    /**
     * Find user profile by user ID.
     * Trouve le profil utilisateur par ID utilisateur.
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Check if user has a profile.
     * Vérifie si l'utilisateur a un profil.
     */
    boolean existsByUser(User user);
} 
package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;

/**
 * User profile service for managing user profile business logic.
 * Service de profil utilisateur pour gérer la logique métier de profil utilisateur.
 */
public interface UserProfileService {

    /**
     * Create a new user profile.
     * Créer un nouveau profil utilisateur.
     */
    UserProfileDto createProfile(User user, CreateUserProfileRequest request);

    /**
     * Create a new user profile by email (for new users).
     * Créer un nouveau profil utilisateur par email (pour les nouveaux utilisateurs).
     */
    UserProfileDto createProfileByEmail(CreateUserProfileWithEmailRequest request);

    /**
     * Get user profile by user entity.
     * Obtenir le profil utilisateur par entité utilisateur.
     */
    UserProfileDto getProfileByUser(User user);

    /**
     * Get user profile by user ID.
     * Obtenir le profil utilisateur par ID utilisateur.
     */
    UserProfileDto getProfileByUserId(Long userId);

    /**
     * Update user profile.
     * Mettre à jour le profil utilisateur.
     */
    UserProfileDto updateProfile(User user, UpdateUserProfileRequest request);

    /**
     * Delete user profile.
     * Supprimer le profil utilisateur.
     */
    void deleteProfile(User user);
} 
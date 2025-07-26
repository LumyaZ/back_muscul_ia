package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;

/**
 * Service interface for UserProfile operations.
 * Interface de service pour les opérations UserProfile.
 * 
 * This service provides methods to manage user profiles including creation,
 * retrieval, updating, and deletion operations. It handles personal information
 * such as name, date of birth, phone number, and other profile-related data.
 * 
 * Ce service fournit des méthodes pour gérer les profils utilisateurs incluant
 * la création, récupération, mise à jour et suppression. Il gère les informations
 * personnelles comme le nom, la date de naissance, le numéro de téléphone et
 * autres données liées au profil.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface UserProfileService {

    /**
     * Create a new user profile.
     * Créer un nouveau profil utilisateur.
     * 
     * @param user - User entity for which to create the profile
     * @param request - Request containing profile data (name, date of birth, phone, etc.)
     * @return UserProfileDto - Created profile with all details
     * @throws RuntimeException if profile already exists for this user or invalid data
     */
    UserProfileDto createProfile(User user, CreateUserProfileRequest request);

    /**
     * Create a new user profile by email (for new users).
     * Créer un nouveau profil utilisateur par email (pour les nouveaux utilisateurs).
     * 
     * @param request - Request containing email and profile data
     * @return UserProfileDto - Created profile with user information
     * @throws RuntimeException if user not found or profile already exists
     */
    UserProfileDto createProfileByEmail(CreateUserProfileWithEmailRequest request);

    /**
     * Get user profile by user entity.
     * Obtenir le profil utilisateur par entité utilisateur.
     * 
     * @param user - User entity
     * @return UserProfileDto - User profile if found
     * @throws RuntimeException if profile not found for this user
     */
    UserProfileDto getProfileByUser(User user);

    /**
     * Get user profile by user ID.
     * Obtenir le profil utilisateur par ID utilisateur.
     * 
     * @param userId - User ID
     * @return UserProfileDto - User profile if found
     * @throws RuntimeException if profile not found for this user ID
     */
    UserProfileDto getProfileByUserId(Long userId);

    /**
     * Update user profile.
     * Mettre à jour le profil utilisateur.
     * 
     * @param user - User entity whose profile to update
     * @param request - Request containing updated profile data
     * @return UserProfileDto - Updated profile
     * @throws RuntimeException if profile not found or invalid data
     */
    UserProfileDto updateProfile(User user, UpdateUserProfileRequest request);

    /**
     * Delete user profile.
     * Supprimer le profil utilisateur.
     * 
     * @param user - User entity whose profile to delete
     * @throws RuntimeException if profile not found
     */
    void deleteProfile(User user);
} 
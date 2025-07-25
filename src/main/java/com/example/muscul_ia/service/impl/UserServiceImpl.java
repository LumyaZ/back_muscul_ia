package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.service.UserService;
import com.example.muscul_ia.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserService for user business logic.
 * Implémentation de UserService pour la logique métier utilisateur.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserProfileService userProfileService;

    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     * 
     * @param request - Données d'inscription
     * @return UserDto - Utilisateur créé
     */
    @Override
    @Transactional
    public UserDto register(RegisterRequest request) {
        // Vérifier que les mots de passe correspondent
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        
        // Vérifier que l'utilisateur n'existe pas déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        // Créer un nouvel utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreationDate(java.time.LocalDateTime.now());

        User savedUser = userRepository.save(user);
        
        return new UserDto(savedUser);
    }

    /**
     * Login an existing user.
     * Connecter un utilisateur existant.
     * 
     * @param request - Données de connexion
     * @return UserDto - Utilisateur connecté
     */
    @Override
    public UserDto login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new UserDto(user);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Create a new user with profile in one request.
     * Créer un nouvel utilisateur avec profil en une seule requête.
     * 
     * @param request - Données utilisateur et profil
     * @return CreateUserWithProfileResponse - Réponse avec utilisateur et profil créés
     */
    @Override
    @Transactional
    public CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request) {
        // Créer d'abord l'utilisateur
        UserDto createdUser = register(request.getUserData());
        
        // Récupérer l'entité utilisateur pour la création du profil
        User user = userRepository.findById(createdUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found after creation"));
        
        // Créer ensuite le profil
        UserProfileDto createdProfile = userProfileService.createProfile(user, request.getProfileData());
        
        CreateUserWithProfileResponse response = new CreateUserWithProfileResponse(createdUser, createdProfile);
        
        return response;
    }
} 
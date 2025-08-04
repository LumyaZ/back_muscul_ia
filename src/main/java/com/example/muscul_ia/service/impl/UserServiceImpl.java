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
 * User service implementation for managing user business logic.
 * Implémentation du service utilisateur pour gérer la logique métier utilisateur.
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
     */
    @Override
    @Transactional
    public UserDto register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

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

    /**
     * Get current authenticated user.
     * Récupérer l'utilisateur actuellement authentifié.
     */
    @Override
    public User getCurrentUser(Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        String email = authentication.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user;
    }

    /**
     * Create a new user with profile in one request.
     * Créer un nouvel utilisateur avec profil en une seule requête.
     */
    @Override
    @Transactional
    public CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request) {
        UserDto createdUser = register(request.getUserData());
        
        User user = userRepository.findByEmail(createdUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after creation"));
        
        UserProfileDto createdProfile = userProfileService.createProfile(user, request.getProfileData());
        
        return new CreateUserWithProfileResponse(createdUser, createdProfile);
    }
} 
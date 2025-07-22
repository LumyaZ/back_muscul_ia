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

    @Override
    @Transactional
    public UserDto register(RegisterRequest request) {
        System.out.println("=== USER SERVICE: REGISTER ===");
        System.out.println("Request: " + request);
        
        // Check if passwords match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            System.out.println("ERROR: Passwords do not match");
            throw new RuntimeException("Passwords do not match");
        }
        
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("ERROR: User already exists with email " + request.getEmail());
            throw new RuntimeException("User already exists with this email");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreationDate(java.time.LocalDateTime.now());

        User savedUser = userRepository.save(user);
        System.out.println("User created successfully: " + savedUser.getId() + " - " + savedUser.getEmail());
        
        return new UserDto(savedUser);
    }

    @Override
    public UserDto login(LoginRequest request) {
        System.out.println("=== USER SERVICE: LOGIN ===");
        System.out.println("Request: " + request);
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("ERROR: Invalid password for user " + request.getEmail());
            throw new RuntimeException("Invalid email or password");
        }

        System.out.println("User logged in successfully: " + user.getId() + " - " + user.getEmail());
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

    @Override
    @Transactional
    public CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request) {
        System.out.println("=== USER SERVICE: CREATE USER WITH PROFILE ===");
        System.out.println("Request: " + request);
        
        // First, create the user
        UserDto createdUser = register(request.getUserData());
        System.out.println("User created: " + createdUser.getId() + " - " + createdUser.getEmail());
        
        // Get the user entity for profile creation
        User user = userRepository.findById(createdUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found after creation"));
        
        // Then, create the profile
        UserProfileDto createdProfile = userProfileService.createProfile(user, request.getProfileData());
        System.out.println("Profile created: " + createdProfile.getId() + " for user " + createdProfile.getUserId());
        
        CreateUserWithProfileResponse response = new CreateUserWithProfileResponse(createdUser, createdProfile);
        System.out.println("Response: " + response);
        
        return response;
    }
} 
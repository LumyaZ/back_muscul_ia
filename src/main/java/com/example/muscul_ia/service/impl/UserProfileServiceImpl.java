package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.repository.UserProfileRepository;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of UserProfileService.
 * Implémentation de UserProfileService.
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserProfileDto createProfile(User user, CreateUserProfileRequest request) {
        System.out.println("=== SERVICE: CREATE PROFILE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        System.out.println("Request: " + request);
        
        // Check if profile already exists
        if (userProfileRepository.existsByUser(user)) {
            System.out.println("ERROR: Profile already exists for user " + user.getId());
            throw new RuntimeException("Profile already exists for this user");
        }

        // Create new profile
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setDateOfBirth(request.getDateOfBirth());
        userProfile.setAge(calculateAge(request.getDateOfBirth()));
        userProfile.setPhoneNumber(request.getPhoneNumber());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        // Save to database
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        System.out.println("Profile created successfully: " + savedProfile.getId());
        System.out.println("=========================");

        // Return DTO
        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional
    public UserProfileDto createProfileByEmail(CreateUserProfileWithEmailRequest request) {
        System.out.println("=== SERVICE: CREATE PROFILE BY EMAIL ===");
        System.out.println("Request: " + request);
        
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
        
        System.out.println("Found user: " + user.getId() + " - " + user.getEmail());

        // Check if profile already exists
        if (userProfileRepository.existsByUser(user)) {
            System.out.println("ERROR: Profile already exists for user " + user.getId());
            throw new RuntimeException("Profile already exists for this user");
        }

        // Create new profile
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setDateOfBirth(request.getDateOfBirth());
        userProfile.setAge(calculateAge(request.getDateOfBirth()));
        userProfile.setPhoneNumber(request.getPhoneNumber());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        // Save to database
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        System.out.println("Profile created successfully: " + savedProfile.getId());
        System.out.println("=========================");

        // Return DTO
        return new UserProfileDto(savedProfile);
    }

    @Override
    public UserProfileDto getProfileByUser(User user) {
        System.out.println("=== SERVICE: GET PROFILE BY USER ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));
        
        System.out.println("Profile found: " + userProfile.getId());
        System.out.println("=========================");
        
        return new UserProfileDto(userProfile);
    }

    @Override
    public UserProfileDto getProfileByUserId(Long userId) {
        System.out.println("=== SERVICE: GET PROFILE BY USER ID ===");
        System.out.println("User ID: " + userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + userId));
        
        System.out.println("Profile found: " + userProfile.getId());
        System.out.println("=========================");
        
        return new UserProfileDto(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(User user, UpdateUserProfileRequest request) {
        System.out.println("=== SERVICE: UPDATE PROFILE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        System.out.println("Request: " + request);
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));

        // Update fields if provided
        if (request.getFirstName() != null) {
            userProfile.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            userProfile.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            userProfile.setDateOfBirth(request.getDateOfBirth());
            userProfile.setAge(calculateAge(request.getDateOfBirth()));
        }
        if (request.getPhoneNumber() != null) {
            userProfile.setPhoneNumber(request.getPhoneNumber());
        }
        
        userProfile.setUpdatedAt(LocalDateTime.now());

        // Save to database
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        System.out.println("Profile updated successfully: " + savedProfile.getId());
        System.out.println("=========================");

        // Return DTO
        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional
    public void deleteProfile(User user) {
        System.out.println("=== SERVICE: DELETE PROFILE ===");
        System.out.println("User: " + user.getId() + " - " + user.getEmail());
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));

        userProfileRepository.delete(userProfile);
        System.out.println("Profile deleted successfully");
        System.out.println("=========================");
    }

    /**
     * Calculate age from date of birth.
     * Calcule l'âge à partir de la date de naissance.
     */
    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - dateOfBirth.getYear();
        if (today.getMonthValue() < dateOfBirth.getMonthValue() || 
            (today.getMonthValue() == dateOfBirth.getMonthValue() && today.getDayOfMonth() < dateOfBirth.getDayOfMonth())) {
            age--;
        }
        return age;
    }
} 
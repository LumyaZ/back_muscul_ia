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

/**
 * User profile service implementation for managing user profile business logic.
 * Implémentation du service de profil utilisateur pour gérer la logique métier de profil utilisateur.
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
        
        if (userProfileRepository.existsByUser(user)) {
            throw new RuntimeException("Profile already exists for this user");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setDateOfBirth(request.getDateOfBirth());
        userProfile.setAge(calculateAge(request.getDateOfBirth()));
        userProfile.setPhoneNumber(request.getPhoneNumber());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        UserProfile savedProfile = userProfileRepository.save(userProfile);

        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional
    public UserProfileDto createProfileByEmail(CreateUserProfileWithEmailRequest request) {
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));
        
        if (userProfileRepository.existsByUser(user)) {
            throw new RuntimeException("Profile already exists for this user");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setDateOfBirth(request.getDateOfBirth());
        userProfile.setAge(calculateAge(request.getDateOfBirth()));
        userProfile.setPhoneNumber(request.getPhoneNumber());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        UserProfile savedProfile = userProfileRepository.save(userProfile);

        return new UserProfileDto(savedProfile);
    }

    @Override
    public UserProfileDto getProfileByUser(User user) {
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));
        
        return new UserProfileDto(userProfile);
    }

    @Override
    public UserProfileDto getProfileByUserId(Long userId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + userId));
        
        return new UserProfileDto(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto updateProfile(User user, UpdateUserProfileRequest request) {
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));

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

        UserProfile savedProfile = userProfileRepository.save(userProfile);

        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional
    public void deleteProfile(User user) {
        
        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getId()));
        
        userProfileRepository.delete(userProfile);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        int age = now.getYear() - dateOfBirth.getYear();
        if (now.isBefore(dateOfBirth.plusYears(age))) {
            age--;
        }
        return age;
    }
} 
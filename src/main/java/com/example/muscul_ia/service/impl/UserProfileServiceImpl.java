package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.repository.UserProfileRepository;
import com.example.muscul_ia.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public UserProfileDto createProfile(User user, CreateUserProfileRequest request) {
        // Check if user already has a profile
        if (userProfileRepository.existsByUser(user)) {
            throw new RuntimeException("L'utilisateur a déjà un profil");
        }

        // Create new profile
        UserProfile profile = new UserProfile(user);
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setPhoneNumber(request.getPhoneNumber());

        // Save profile
        UserProfile savedProfile = userProfileRepository.save(profile);
        
        // Update user with profile
        user.setUserProfile(savedProfile);
        
        return new UserProfileDto(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileDto> getProfileByUser(User user) {
        return userProfileRepository.findByUser(user)
                .map(UserProfileDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileDto> getProfileByUserId(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .map(UserProfileDto::new);
    }

    @Override
    public UserProfileDto updateProfile(User user, UpdateUserProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));

        if (request.getFirstName() != null) {
            profile.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            profile.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            profile.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getPhoneNumber() != null) {
            profile.setPhoneNumber(request.getPhoneNumber());
        }

        UserProfile updatedProfile = userProfileRepository.save(profile);
        return new UserProfileDto(updatedProfile);
    }

    @Override
    public void deleteProfile(User user) {
        UserProfile profile = userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
        
        userProfileRepository.delete(profile);
        user.setUserProfile(null);
    }
} 
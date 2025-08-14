package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.UserProfileService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * User profile controller for managing user profiles.
 */
@RestController
@RequestMapping("/api/profiles")
@Tag(name = "User Profiles", description = "User profile management endpoints")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    /**
     * Create a new user profile (requires authentication).
     */
    @PostMapping
    @Operation(summary = "Create user profile")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User profile created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserProfileDto> createProfile(
            @Valid @RequestBody CreateUserProfileRequest request,
            Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        logger.info("Creating user profile for user: {}", user.getEmail());
        UserProfileDto createdProfile = userProfileService.createProfile(user, request);
        logger.info("User profile created successfully for user: {}", user.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Create a new user profile by email (public endpoint).
     */
    @PostMapping("/public")
    @Operation(summary = "Create user profile by email")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User profile created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<UserProfileDto> createProfileByEmail(
            @Valid @RequestBody CreateUserProfileWithEmailRequest request) {
        
        logger.info("Creating user profile by email: {}", request.getEmail());
        UserProfileDto createdProfile = userProfileService.createProfileByEmail(request);
        logger.info("User profile created successfully by email: {}", request.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Get the current user's profile.
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully")
    public ResponseEntity<UserProfileDto> getMyProfile(Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        logger.info("Retrieving profile for user: {}", user.getEmail());
        UserProfileDto profile = userProfileService.getProfileByUser(user);
        
        return ResponseEntity.ok(profile);
    }

    /**
     * Get a user profile by user ID.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile by user ID")
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully")
    public ResponseEntity<UserProfileDto> getProfileByUserId(@PathVariable Long userId) {
        
        logger.info("Retrieving user profile for user ID: {}", userId);
        UserProfileDto profile = userProfileService.getProfileByUserId(userId);
        
        return ResponseEntity.ok(profile);
    }

    /**
     * Update the current user's profile.
     */
    @PutMapping("/me")
    @Operation(summary = "Update current user profile")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserProfileDto> updateMyProfile(
            @Valid @RequestBody UpdateUserProfileRequest request,
            Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        logger.info("Updating profile for user: {}", user.getEmail());
        UserProfileDto updatedProfile = userProfileService.updateProfile(user, request);
        logger.info("Profile updated successfully for user: {}", user.getEmail());
        
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Delete the current user's profile.
     */
    @DeleteMapping("/me")
    @Operation(summary = "Delete current user profile")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User profile deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteMyProfile(Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        logger.info("Deleting profile for user: {}", user.getEmail());
        userProfileService.deleteProfile(user);
        logger.info("Profile deleted successfully for user: {}", user.getEmail());
        
        return ResponseEntity.noContent().build();
    }
} 
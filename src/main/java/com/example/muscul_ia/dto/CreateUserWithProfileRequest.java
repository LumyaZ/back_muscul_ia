package com.example.muscul_ia.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a user with profile in one request.
 * DTO pour créer un utilisateur avec profil en une seule requête.
 */
public class CreateUserWithProfileRequest {

    @NotNull(message = "Les données utilisateur sont obligatoires")
    @Valid
    private RegisterRequest userData;

    @NotNull(message = "Les données de profil sont obligatoires")
    @Valid
    private CreateUserProfileRequest profileData;

    // Constructors
    public CreateUserWithProfileRequest() {}

    public CreateUserWithProfileRequest(RegisterRequest userData, CreateUserProfileRequest profileData) {
        this.userData = userData;
        this.profileData = profileData;
    }

    // Getters and Setters
    public RegisterRequest getUserData() {
        return userData;
    }

    public void setUserData(RegisterRequest userData) {
        this.userData = userData;
    }

    public CreateUserProfileRequest getProfileData() {
        return profileData;
    }

    public void setProfileData(CreateUserProfileRequest profileData) {
        this.profileData = profileData;
    }

    @Override
    public String toString() {
        return "CreateUserWithProfileRequest{" +
                "userData=" + userData +
                ", profileData=" + profileData +
                '}';
    }
} 
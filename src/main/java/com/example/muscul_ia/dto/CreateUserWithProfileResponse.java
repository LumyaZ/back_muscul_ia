package com.example.muscul_ia.dto;

/**
 * DTO for the response when creating a user with profile.
 * DTO pour la réponse lors de la création d'un utilisateur avec profil.
 */
public class CreateUserWithProfileResponse {

    private UserDto user;
    private UserProfileDto profile;

    // Constructors
    public CreateUserWithProfileResponse() {}

    public CreateUserWithProfileResponse(UserDto user, UserProfileDto profile) {
        this.user = user;
        this.profile = profile;
    }

    // Getters and Setters
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public UserProfileDto getProfile() {
        return profile;
    }

    public void setProfile(UserProfileDto profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "CreateUserWithProfileResponse{" +
                "user=" + user +
                ", profile=" + profile +
                '}';
    }
} 
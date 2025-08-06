package com.example.muscul_ia.enums;

/**
 * Enum class for gender types.
 * Enumération pour les types de genre.
 */
public enum Gender {
    MALE("Homme"),
    FEMALE("Femme"),
    OTHER("Autre");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 
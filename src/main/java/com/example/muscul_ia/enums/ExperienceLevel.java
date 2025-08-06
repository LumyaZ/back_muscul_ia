package com.example.muscul_ia.enums;

/**
 * Enum class for experience levels.
 * Enumération pour les niveaux d'expérience.
 */
public enum ExperienceLevel {
    BEGINNER("Débutant", "0-1 an"),
    INTERMEDIATE("Intermédiaire", "1-3 ans"),
    ADVANCED("Avancé", "3-5 ans"),
    EXPERT("Expert", "5+ ans");

    private final String displayName;
    private final String description;

    ExperienceLevel(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
} 
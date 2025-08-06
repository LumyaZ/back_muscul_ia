package com.example.muscul_ia.enums;

/**
 * Enum class for session durations.
 * Enumération pour les durées des sessions.
 */
public enum SessionDuration {
    SHORT("Court", "30-45 minutes"),
    MEDIUM("Moyen", "45-60 minutes"),
    LONG("Long", "60-90 minutes"),
    EXTENDED("Étendu", "90+ minutes");

    private final String displayName;
    private final String description;

    SessionDuration(String displayName, String description) {
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
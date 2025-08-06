package com.example.muscul_ia.enums;

/**
 * Enum class for session frequencies.
 * Enumération pour les fréquences des sessions.
 */
public enum SessionFrequency {
    ONE_TO_TWO("1-2 fois", "1-2 sessions par semaine"),
    THREE_TO_FOUR("3-4 fois", "3-4 sessions par semaine"),
    FIVE_TO_SIX("5-6 fois", "5-6 sessions par semaine"),
    DAILY("Quotidien", "7 sessions par semaine");

    private final String displayName;
    private final String description;

    SessionFrequency(String displayName, String description) {
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
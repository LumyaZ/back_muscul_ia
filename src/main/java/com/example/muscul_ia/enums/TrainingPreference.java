package com.example.muscul_ia.enums;

public enum TrainingPreference {
    CARDIO("Cardio", "Entraînement cardiovasculaire"),
    STRENGTH_TRAINING("Musculation", "Entraînement de force"),
    FUNCTIONAL("Fonctionnel", "Entraînement fonctionnel"),
    FLEXIBILITY("Flexibilité", "Étirements et yoga"),
    SPORTS("Sports", "Sports spécifiques"),
    MIXED("Mixte", "Combinaison de différents types");

    private final String displayName;
    private final String description;

    TrainingPreference(String displayName, String description) {
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
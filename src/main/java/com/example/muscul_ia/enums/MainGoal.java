package com.example.muscul_ia.enums;

public enum MainGoal {
    WEIGHT_LOSS("Perte de poids", "Réduire la masse graisseuse"),
    MUSCLE_GAIN("Prise de masse", "Augmenter la masse musculaire"),
    STRENGTH("Force", "Améliorer la force"),
    ENDURANCE("Endurance", "Améliorer l'endurance"),
    TONING("Tonification", "Tonifier les muscles"),
    GENERAL_FITNESS("Forme générale", "Maintenir une bonne forme physique");

    private final String displayName;
    private final String description;

    MainGoal(String displayName, String description) {
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
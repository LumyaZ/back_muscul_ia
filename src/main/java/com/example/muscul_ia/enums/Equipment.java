package com.example.muscul_ia.enums;

/**
 * Enum class for equipment types.
 * Enumération pour les types d'équipement.
 */
public enum Equipment {
    NONE("Aucun", "Pas d'équipement"),
    BASIC("Basique", "Poids libres, tapis"),
    HOME_GYM("Salle à domicile", "Équipement complet à domicile"),
    GYM_ACCESS("Accès salle", "Accès à une salle de sport"),
    FULL_EQUIPMENT("Équipement complet", "Tous types d'équipements");

    private final String displayName;
    private final String description;

    Equipment(String displayName, String description) {
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
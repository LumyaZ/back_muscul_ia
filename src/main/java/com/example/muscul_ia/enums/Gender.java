package com.example.muscul_ia.enums;

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
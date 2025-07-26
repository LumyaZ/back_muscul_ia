package com.example.muscul_ia.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Data Transfer Object for updating user profile information.
 * Objet de transfert de données pour la mise à jour des informations de profil utilisateur.
 * 
 * This DTO is used to transfer user profile update data from the client to the server.
 * It contains validation annotations to ensure data integrity and includes fields
 * for updating personal information such as first name, last name, date of birth,
 * and phone number.
 * 
 * Ce DTO est utilisé pour transférer les données de mise à jour de profil utilisateur
 * du client vers le serveur. Il contient des annotations de validation pour assurer
 * l'intégrité des données et inclut des champs pour mettre à jour les informations
 * personnelles telles que le prénom, le nom de famille, la date de naissance et
 * le numéro de téléphone.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class UpdateUserProfileRequest {
    
    /**
     * User's first name for profile update.
     * Prénom de l'utilisateur pour la mise à jour du profil.
     * 
     * Must be between 2 and 50 characters.
     * Doit contenir entre 2 et 50 caractères.
     */
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstName;

    /**
     * User's last name for profile update.
     * Nom de famille de l'utilisateur pour la mise à jour du profil.
     * 
     * Must be between 2 and 50 characters.
     * Doit contenir entre 2 et 50 caractères.
     */
    @Size(min = 2, max = 50, message = "Le nom de famille doit contenir entre 2 et 50 caractères")
    private String lastName;

    /**
     * User's date of birth for profile update.
     * Date de naissance de l'utilisateur pour la mise à jour du profil.
     * 
     * Must be a date in the past.
     * Doit être une date dans le passé.
     */
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateOfBirth;

    /**
     * User's phone number for profile update.
     * Numéro de téléphone de l'utilisateur pour la mise à jour du profil.
     * 
     * Must match the pattern for valid phone numbers.
     * Doit correspondre au modèle pour les numéros de téléphone valides.
     */
    @Pattern(regexp = "^[+]?[0-9\\s\\-()]{10,20}$", message = "Le numéro de téléphone n'est pas valide")
    private String phoneNumber;

    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public UpdateUserProfileRequest() {}
    
    /**
     * Constructor with all profile update parameters.
     * Constructeur avec tous les paramètres de mise à jour de profil.
     * 
     * @param firstName - User's first name
     * @param lastName - User's last name
     * @param dateOfBirth - User's date of birth
     * @param phoneNumber - User's phone number
     */
    public UpdateUserProfileRequest(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the first name.
     * Récupérer le prénom.
     * 
     * @return String - First name
     */
    public String getFirstName() { 
        return firstName; 
    }
    
    /**
     * Set the first name.
     * Définir le prénom.
     * 
     * @param firstName - First name
     */
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }

    /**
     * Get the last name.
     * Récupérer le nom de famille.
     * 
     * @return String - Last name
     */
    public String getLastName() { 
        return lastName; 
    }
    
    /**
     * Set the last name.
     * Définir le nom de famille.
     * 
     * @param lastName - Last name
     */
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }

    /**
     * Get the date of birth.
     * Récupérer la date de naissance.
     * 
     * @return LocalDate - Date of birth
     */
    public LocalDate getDateOfBirth() { 
        return dateOfBirth; 
    }
    
    /**
     * Set the date of birth.
     * Définir la date de naissance.
     * 
     * @param dateOfBirth - Date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    }

    /**
     * Get the phone number.
     * Récupérer le numéro de téléphone.
     * 
     * @return String - Phone number
     */
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    /**
     * Set the phone number.
     * Définir le numéro de téléphone.
     * 
     * @param phoneNumber - Phone number
     */
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
} 
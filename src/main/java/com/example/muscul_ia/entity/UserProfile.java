package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * User profile entity containing personal information.
 * Entité profil utilisateur contenant les informations personnelles.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with User.
     * Relation un-à-un avec User.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * First name of the user.
     * Prénom de l'utilisateur.
     */
    @Column(name = "first_name", length = 50)
    private String firstName;

    /**
     * Last name of the user.
     * Nom de famille de l'utilisateur.
     */
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * Date of birth.
     * Date de naissance.
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Age calculated from date of birth.
     * Âge calculé à partir de la date de naissance.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Phone number.
     * Numéro de téléphone.
     */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * Profile creation date.
     * Date de création du profil.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Profile last update date.
     * Date de dernière modification du profil.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public UserProfile() {
        this.createdAt = LocalDateTime.now();
    }

    public UserProfile(User user) {
        this();
        this.user = user;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth;
        this.calculateAge();
    }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }


    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    /**
     * Calculate age from date of birth.
     * Calcule l'âge à partir de la date de naissance.
     */
    private void calculateAge() {
        if (this.dateOfBirth != null) {
            this.age = LocalDate.now().getYear() - this.dateOfBirth.getYear();
            if (LocalDate.now().isBefore(this.dateOfBirth.plusYears(this.age))) {
                this.age--;
            }
        }
    }

    /**
     * Update the updatedAt timestamp.
     * Met à jour le timestamp de modification.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Get full name (first name + last name).
     * Obtient le nom complet (prénom + nom).
     */
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return null;
    }
} 
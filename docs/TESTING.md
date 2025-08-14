# Guide de Tests - Backend Muscul IA

## Vue d'ensemble / Overview

Ce guide détaille les tests unitaires et d'intégration du backend Spring Boot, conformément aux exigences C2.2.2 et C2.2.4.

This guide details the unit and integration tests for the Spring Boot backend, in compliance with C2.2.2 and C2.2.4 requirements.

## Tests Unitaires (C2.2.2)

### Exécution des tests / Running Tests

```bash
# Tous les tests / All tests
mvn test

# Tests spécifiques / Specific tests
mvn test -Dtest=AuthControllerTest

# Tests avec couverture / Tests with coverage
mvn test jacoco:report

# Tests avec rapport détaillé / Tests with detailed report
mvn test jacoco:report jacoco:check
```

### Couverture de code / Code Coverage

- **Seuil minimum** : 80%
- **Rapport** : `target/site/jacoco/index.html`
- **Vérification** : `mvn jacoco:check`

### Tests disponibles / Available Tests

#### Contrôleurs / Controllers
- `AuthControllerTest` : Tests d'authentification / Authentication tests
- `UserControllerTest` : Tests de gestion utilisateur / User management tests
- `ExerciseControllerTest` : Tests des exercices / Exercise tests
- `ProgramExerciseControllerTest` : Tests des programmes / Program tests
- `TrainingInfoControllerTest` : Tests des informations d'entraînement / Training info tests

#### Services / Services
- `UserServiceTest` : Tests du service utilisateur / User service tests
- `JwtServiceTest` : Tests du service JWT / JWT service tests
- `ExerciseServiceTest` : Tests du service exercices / Exercise service tests

#### Entités / Entities
- `UserTest` : Tests de l'entité utilisateur / User entity tests
- `ExerciseTest` : Tests de l'entité exercice / Exercise entity tests
- `TrainingInfoTest` : Tests de l'entité info entraînement / Training info entity tests

## Tests d'Intégration (C2.2.4)

### Tests des endpoints / Endpoint Tests

#### Authentification / Authentication
```java
@Test
public void testUserRegistration() {
    // Test d'inscription utilisateur / User registration test
}

@Test
public void testUserLogin() {
    // Test de connexion utilisateur / User login test
}
```

#### Gestion des exercices / Exercise Management
```java
@Test
public void testCreateExercise() {
    // Test de création d'exercice / Exercise creation test
}

@Test
public void testGetAllExercises() {
    // Test de récupération des exercices / Exercise retrieval test
}
```

### Validation des données / Data Validation

- Tests de validation avec `@Valid`
- Tests des contraintes de données
- Tests des réponses d'erreur

### Tests de sécurité / Security Tests

```java
@Test
public void testUnauthorizedAccess() {
    // Test d'accès non autorisé / Unauthorized access test
}

@Test
public void testJwtTokenValidation() {
    // Test de validation du token JWT / JWT token validation test
}
```

## Qualité du Code / Code Quality

### Checkstyle
```bash
# Vérification du style / Style check
mvn checkstyle:check

# Rapport détaillé / Detailed report
mvn checkstyle:checkstyle
```

### SpotBugs
```bash
# Analyse des bugs / Bug analysis
mvn spotbugs:check

# Rapport XML / XML report
mvn spotbugs:spotbugs
```

### JaCoCo
```bash
# Rapport de couverture / Coverage report
mvn jacoco:report

# Vérification des seuils / Threshold verification
mvn jacoco:check
```

## Scripts de Test / Test Scripts

### Script de qualité complet / Complete quality script
```bash
./scripts/quality-check.sh
```

### Script de test rapide / Quick test script
```bash
mvn clean test
```

## Rapports disponibles / Available Reports

### Couverture de code / Code Coverage
- **Localisation** : `target/site/jacoco/index.html`
- **Métriques** : Lignes, branches, méthodes, classes
- **Seuils** : 80% minimum pour les lignes

### SpotBugs
- **Localisation** : `target/spotbugs/spotbugsXml.xml`
- **Types** : Bugs potentiels, mauvaises pratiques
- **Niveaux** : High, Medium, Low

### Checkstyle
- **Sortie** : Console
- **Règles** : Style de code, conventions Java
- **Configuration** : `config/checkstyle.xml`

## Intégration Continue / Continuous Integration

### Pipeline de test / Test Pipeline
1. **Compilation** : `mvn clean compile`
2. **Tests unitaires** : `mvn test`
3. **Couverture** : `mvn jacoco:report`
4. **Qualité** : `mvn checkstyle:check spotbugs:check`
5. **Packaging** : `mvn package`

### Seuils de qualité / Quality Thresholds
- **Couverture de code** : ≥ 80%
- **Checkstyle** : 0 erreurs
- **SpotBugs** : 0 erreurs High
- **Tests** : 100% de succès

## Dépannage / Troubleshooting

### Erreurs communes / Common Errors

#### Tests qui échouent / Failing Tests
```bash
# Voir les détails / See details
mvn test -Dtest=TestClassName -DfailIfNoTests=false

# Mode debug / Debug mode
mvn test -X
```

#### Couverture insuffisante / Insufficient Coverage
```bash
# Rapport détaillé / Detailed report
mvn jacoco:report
# Ouvrir target/site/jacoco/index.html
```

#### Erreurs Checkstyle / Checkstyle Errors
```bash
# Voir les erreurs / See errors
mvn checkstyle:check

# Corriger automatiquement / Auto-fix
# Utiliser votre IDE pour corriger le style
```

## Conformité aux Blocs / Block Compliance

### C2.2.2 - Tests Unitaires
✅ **Implémenté** : Tests unitaires complets pour tous les services et contrôleurs
✅ **Couverture** : JaCoCo configuré avec seuil de 80%
✅ **Rapports** : Génération automatique des rapports

### C2.2.4 - Tests d'Intégration
✅ **Implémenté** : Tests d'intégration pour tous les endpoints
✅ **Validation** : Tests de validation des données
✅ **Sécurité** : Tests d'authentification et d'autorisation

### C4.2.1 - Logging
✅ **Implémenté** : Logging structuré dans les contrôleurs
✅ **Analyse** : Script d'analyse des logs
✅ **Monitoring** : Intégration avec Spring Boot Actuator 
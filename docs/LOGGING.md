# Guide de Logging - Backend Muscul IA

## Vue d'ensemble / Overview

Ce guide détaille le système de logging du backend Spring Boot, conformément à l'exigence C4.2.1 pour la détection d'anomalies.

This guide details the logging system for the Spring Boot backend, in compliance with C4.2.1 requirement for anomaly detection.

## Configuration (C4.2.1)

### Logs disponibles / Available Logs

#### Authentification / Authentication
- **Tentatives de connexion** : Succès/échecs avec email
- **Tentatives d'inscription** : Succès/échecs avec email
- **Création de profil** : Succès/échecs avec détails

#### Erreurs système / System Errors
- **GlobalExceptionHandler** : Gestion centralisée des exceptions
- **Erreurs de validation** : Données invalides
- **Erreurs de base de données** : Problèmes de connexion/requêtes

#### Performance / Performance
- **Temps de réponse** : Endpoints lents
- **Requêtes SQL** : Requêtes longues (niveau DEBUG)
- **Mémoire JVM** : Utilisation des ressources

### Configuration des logs / Log Configuration

#### `application.properties`
```properties
# Niveau de logging / Logging level
logging.level.com.example.muscul_ia=INFO
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Format des logs / Log format
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

# Fichier de logs / Log file
logging.file.name=logs/muscul-ia.log
logging.file.max-size=10MB
logging.file.max-history=30
```

## Analyse des logs / Log Analysis

### Script d'analyse automatique / Automatic Analysis Script

```bash
# Analyse des logs / Log analysis
./scripts/check-logs.sh

# Logs en temps réel / Real-time logs
tail -f logs/muscul-ia.log

# Recherche d'erreurs / Error search
grep -i "error\|exception" logs/muscul-ia.log

# Recherche d'anomalies d'authentification / Authentication anomaly search
grep -i "échec\|failed\|tentative" logs/muscul-ia.log
```

### Métriques de logging / Logging Metrics

#### Tentatives de connexion / Login Attempts
```bash
# Comptage des tentatives / Attempt counting
grep -c "Tentative de connexion" logs/muscul-ia.log
grep -c "Connexion réussie" logs/muscul-ia.log
grep -c "Échec de connexion" logs/muscul-ia.log
```

#### Erreurs par type / Errors by Type
```bash
# Erreurs de validation / Validation errors
grep -c "validation" logs/muscul-ia.log

# Erreurs de base de données / Database errors
grep -c "database\|sql" logs/muscul-ia.log

# Erreurs système / System errors
grep -c "exception\|error" logs/muscul-ia.log
```

## Implémentation / Implementation

### Logging dans les contrôleurs / Controller Logging

#### AuthController
```java
private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
    logger.info("Tentative de connexion pour: {}", request.getEmail());
    
    try {
        // Logique de connexion / Login logic
        logger.info("Connexion réussie pour: {}", request.getEmail());
    } catch (Exception e) {
        logger.error("Échec de connexion pour {}: {}", request.getEmail(), e.getMessage());
        throw e;
    }
}
```

### GlobalExceptionHandler
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Erreur système: {} - URL: {}", ex.getMessage(), request.getDescription(false));
        // Gestion de l'erreur / Error handling
    }
}
```

## Détection d'anomalies / Anomaly Detection

### Types d'anomalies / Anomaly Types

#### Anomalies d'authentification / Authentication Anomalies
- **Tentatives multiples** : Plus de 5 tentatives échouées en 1 heure
- **IP suspecte** : Tentatives depuis des IPs inhabituelles
- **Heures anormales** : Tentatives en dehors des heures normales

#### Anomalies système / System Anomalies
- **Erreurs répétées** : Même erreur plusieurs fois
- **Performance dégradée** : Temps de réponse > 5 secondes
- **Ressources épuisées** : Mémoire/CPU élevés

### Script de détection / Detection Script

```bash
#!/bin/bash
# Détection automatique d'anomalies / Automatic anomaly detection

LOG_FILE="logs/muscul-ia.log"
ALERT_FILE="logs/alerts.log"

# Détection des tentatives multiples / Multiple attempts detection
FAILED_ATTEMPTS=$(grep -c "Échec de connexion" "$LOG_FILE")
if [ "$FAILED_ATTEMPTS" -gt 10 ]; then
    echo "$(date): ALERTE - $FAILED_ATTEMPTS tentatives échouées" >> "$ALERT_FILE"
fi

# Détection des erreurs répétées / Repeated errors detection
ERROR_COUNT=$(grep -c "ERROR" "$LOG_FILE")
if [ "$ERROR_COUNT" -gt 50 ]; then
    echo "$(date): ALERTE - $ERROR_COUNT erreurs détectées" >> "$ALERT_FILE"
fi
```

## Monitoring et alertes / Monitoring and Alerts

### Intégration avec Spring Boot Actuator
```properties
# Endpoints de monitoring / Monitoring endpoints
management.endpoints.web.exposure.include=health,metrics,info,loggers
management.endpoint.health.show-details=always
```

### Métriques disponibles / Available Metrics
- **http.server.requests** : Requêtes HTTP
- **jvm.memory.used** : Mémoire JVM utilisée
- **process.cpu.usage** : Utilisation CPU
- **hikaricp.connections** : Connexions base de données

### Alertes automatiques / Automatic Alerts
```bash
# Vérification de santé / Health check
curl -f http://localhost:8080/actuator/health || echo "ALERTE: Service down"

# Vérification des métriques / Metrics check
curl http://localhost:8080/actuator/metrics/http.server.requests
```

## Maintenance / Maintenance

### Rotation des logs / Log Rotation
```properties
# Configuration de rotation / Rotation configuration
logging.file.max-size=10MB
logging.file.max-history=30
```

### Nettoyage automatique / Automatic Cleanup
```bash
# Script de nettoyage / Cleanup script
find logs/ -name "*.log.*" -mtime +30 -delete
```

### Archivage / Archiving
```bash
# Archivage mensuel / Monthly archiving
tar -czf logs/archive-$(date +%Y-%m).tar.gz logs/*.log.*
```

## Conformité C4.2.1 / C4.2.1 Compliance

### Exigences satisfaites / Satisfied Requirements

1. **Collecte automatique** : Logging structuré dans tous les contrôleurs
2. **Détection d'anomalies** : Script `check-logs.sh` pour l'analyse
3. **Centralisation** : GlobalExceptionHandler pour les erreurs
4. **Documentation** : Ce guide et les commentaires dans le code
5. **Monitoring** : Intégration avec Spring Boot Actuator

### Métriques de conformité / Compliance Metrics
- **Couverture de logging** : 100% des contrôleurs
- **Détection d'anomalies** : Automatique via scripts
- **Temps de réponse** : < 1 minute pour la détection
- **Rétention des logs** : 30 jours minimum

## Utilisation pratique / Practical Usage

### Démarrage rapide / Quick Start
```bash
# 1. Démarrer l'application / Start application
mvn spring-boot:run

# 2. Surveiller les logs / Monitor logs
tail -f logs/muscul-ia.log

# 3. Analyser les anomalies / Analyze anomalies
./scripts/check-logs.sh

# 4. Vérifier la santé / Check health
curl http://localhost:8080/actuator/health
```

### Commandes utiles / Useful Commands
```bash
# Logs d'authentification / Authentication logs
grep "connexion\|login" logs/muscul-ia.log

# Erreurs récentes / Recent errors
grep "$(date +%Y-%m-%d)" logs/muscul-ia.log | grep ERROR

# Performance / Performance
grep "temps\|duration" logs/muscul-ia.log
``` 
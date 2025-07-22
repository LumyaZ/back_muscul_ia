# Guide de Qualité du Code - Back-end

## 📊 Outils de Qualité

### 1. Checkstyle
Vérifie la conformité du code aux standards de codage Java.

**Lancement :**
```bash
./mvnw checkstyle:check
```

**Configuration :** `checkstyle.xml`
- Longueur de ligne max : 120 caractères
- Longueur de méthode max : 150 lignes
- Nombre de paramètres max : 7
- Conventions de nommage Java
- Espacement et formatage

### 2. SpotBugs
Détecte les bugs potentiels dans le code Java.

**Lancement :**
```bash
./mvnw spotbugs:check
```

**Rapport :** `target/spotbugs/spotbugsXml.xml`

### 3. JaCoCo (Couverture de Code)
Mesure la couverture de code par les tests.

**Lancement :**
```bash
./mvnw test jacoco:report
```

**Seuil minimum :** 80% de couverture de lignes
**Rapport :** `target/site/jacoco/index.html`

## 📈 Métriques de Performance

### Endpoints de Monitoring
- **Health Check :** `http://localhost:8080/actuator/health`
- **Métriques :** `http://localhost:8080/actuator/metrics`
- **Prometheus :** `http://localhost:8080/actuator/prometheus`

### Métriques disponibles
- Temps de réponse des endpoints
- Nombre de requêtes par seconde
- Utilisation mémoire et CPU
- Connexions base de données
- Erreurs HTTP

## 📝 Logs Structurés

### Configuration
```properties
# Niveaux de logs
logging.level.com.example.muscul_ia=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# Format structuré
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Logs importants à surveiller
- **Authentification :** Tentatives de connexion, échecs
- **Base de données :** Requêtes lentes, erreurs de connexion
- **Sécurité :** Tentatives d'accès non autorisées
- **Performance :** Temps de réponse des API

## 🔧 Scripts Automatisés

### Vérification complète
```bash
./quality-check.sh
```

Ce script exécute :
1. ✅ Checkstyle
2. ✅ SpotBugs
3. ✅ Tests avec couverture
4. ✅ Compilation
5. ✅ Analyse des dépendances

### Commandes individuelles
```bash
# Qualité du code
./mvnw checkstyle:check
./mvnw spotbugs:check

# Tests et couverture
./mvnw test jacoco:report

# Compilation
./mvnw clean compile

# Dépendances
./mvnw dependency:analyze
```

## 🎯 Objectifs de Qualité

### Couverture de Code
- **Minimum :** 80% de lignes couvertes
- **Objectif :** 90% de lignes couvertes
- **Branches :** 70% minimum

### Standards de Code
- **Checkstyle :** 0 erreur, max 10 warnings
- **SpotBugs :** 0 bug critique ou élevé
- **Compilation :** 0 erreur de compilation

### Performance
- **Temps de réponse API :** < 500ms
- **Temps de démarrage :** < 30s
- **Utilisation mémoire :** < 512MB

## 📊 Rapports et Dashboards

### Rapports générés
- **JaCoCo :** `target/site/jacoco/index.html`
- **SpotBugs :** `target/spotbugs/spotbugsXml.xml`
- **Maven :** `target/site/project-reports.html`

### Intégration CI/CD
```yaml
# Exemple GitHub Actions
- name: Quality Check
  run: |
    ./mvnw checkstyle:check
    ./mvnw spotbugs:check
    ./mvnw test jacoco:report
```

## 🚨 Alertes et Monitoring

### Métriques critiques
- **Erreurs 5xx :** > 1% des requêtes
- **Temps de réponse :** > 1s
- **Couverture de code :** < 80%
- **Mémoire utilisée :** > 80%

### Actions automatiques
- Notification Slack/Email en cas d'alerte
- Arrêt automatique si couverture < 70%
- Rollback si performance dégradée

## 📚 Ressources

### Documentation
- [Checkstyle](https://checkstyle.org/)
- [SpotBugs](https://spotbugs.github.io/)
- [JaCoCo](https://www.jacoco.org/jacoco/)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

### Bonnes pratiques
- Exécuter les vérifications avant chaque commit
- Maintenir la couverture de code > 80%
- Réviser les rapports SpotBugs régulièrement
- Monitorer les métriques en production

---

**Dernière mise à jour :** Janvier 2024 
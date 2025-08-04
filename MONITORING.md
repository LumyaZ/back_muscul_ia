# Monitoring et Métriques - Muscul IA

## Vue d'ensemble

Ce document décrit la configuration du monitoring et des métriques de performance pour l'application Muscul IA.

## Composants de monitoring

### 1. Spring Boot Actuator
- **Endpoint**: `/actuator`
- **Métriques**: `/actuator/metrics`
- **Prometheus**: `/actuator/prometheus`
- **Health**: `/actuator/health`

### 2. Prometheus
- **Port**: 9090
- **Configuration**: `monitoring/prometheus.yml`
- **Métriques collectées**:
  - Métriques JVM
  - Métriques système
  - Métriques d'application
  - Temps de réponse des endpoints

### 3. Grafana (optionnel)
- **Port**: 3000
- **Credentials**: admin/admin
- **Dashboards**: Monitoring des performances

## Installation

```bash
# Configuration du monitoring
./scripts/monitoring-setup.sh

# Vérification
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/prometheus
```

## Métriques disponibles

### Métriques système
- `jvm_memory_used_bytes`
- `jvm_memory_max_bytes`
- `process_cpu_usage`
- `system_cpu_usage`

### Métriques d'application
- `http_server_requests_seconds`
- `http_server_requests_total`
- `hikaricp_connections_active`
- `hikaricp_connections_idle`

### Métriques personnalisées
- Temps d'exécution des méthodes
- Nombre d'utilisateurs connectés
- Performance des requêtes

## Alertes

Les alertes suivantes sont configurées :
- CPU > 80%
- Mémoire > 85%
- Temps de réponse > 2s
- Erreurs HTTP > 5%

## Maintenance

```bash
# Redémarrage du monitoring
docker restart prometheus grafana

# Logs
docker logs prometheus
docker logs grafana
``` 
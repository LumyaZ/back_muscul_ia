#!/bin/bash

# Script de configuration du monitoring pour Muscul IA
# Monitoring setup script for Muscul IA

echo "Configuration du monitoring Muscul IA..."

# Vérification des dépendances
if ! command -v docker &> /dev/null; then
    echo "Docker n'est pas installé"
    exit 1
fi

# Création du réseau Docker pour le monitoring
echo "📡 Création du réseau monitoring..."
docker network create monitoring-network 2>/dev/null || echo "Réseau monitoring déjà existant"

# Démarrage de Prometheus
echo "Démarrage de Prometheus..."
docker run -d \
    --name prometheus \
    --network monitoring-network \
    -p 9090:9090 \
    -v $(pwd)/monitoring/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus

# Démarrage de Grafana (optionnel)
echo "Démarrage de Grafana..."
docker run -d \
    --name grafana \
    --network monitoring-network \
    -p 3000:3000 \
    -e GF_SECURITY_ADMIN_PASSWORD=admin \
    grafana/grafana

echo "Monitoring configuré !"
echo "Prometheus: http://localhost:9090"
echo "Grafana: http://localhost:3000 (admin/admin)"
echo "Métriques de l'app: http://localhost:8080/actuator/prometheus" 
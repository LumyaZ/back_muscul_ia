#!/bin/bash

# Script d'analyse des logs pour détecter les anomalies
# Log analysis script to detect anomalies

echo "=== Analyse des logs Muscul IA ==="
echo "Date: $(date)"
echo ""

# Vérifier si le fichier de log existe
if [ ! -f "logs/muscul-ia.log" ]; then
    echo "❌ Fichier de log non trouvé: logs/muscul-ia.log"
    exit 1
fi

echo "📊 Statistiques générales:"
echo "Nombre total de lignes: $(wc -l < logs/muscul-ia.log)"
echo ""

echo "🚨 Erreurs détectées (dernières 10):"
grep -i "error\|exception" logs/muscul-ia.log | tail -10 | while read line; do
    echo "  ❌ $line"
done
echo ""

echo "🔐 Tentatives de connexion (dernières 10):"
grep -i "connexion\|login" logs/muscul-ia.log | tail -10 | while read line; do
    echo "  🔑 $line"
done
echo ""

echo "⚠️ Avertissements (dernières 5):"
grep -i "warn" logs/muscul-ia.log | tail -5 | while read line; do
    echo "  ⚠️ $line"
done
echo ""

echo "✅ Connexions réussies (dernières 5):"
grep -i "connexion réussie" logs/muscul-ia.log | tail -5 | while read line; do
    echo "  ✅ $line"
done
echo ""

echo "📈 Résumé des anomalies:"
ERROR_COUNT=$(grep -i "error\|exception" logs/muscul-ia.log | wc -l)
LOGIN_ATTEMPTS=$(grep -i "tentative de connexion" logs/muscul-ia.log | wc -l)
SUCCESSFUL_LOGINS=$(grep -i "connexion réussie" logs/muscul-ia.log | wc -l)
FAILED_LOGINS=$(grep -i "échec de connexion" logs/muscul-ia.log | wc -l)

echo "  Erreurs totales: $ERROR_COUNT"
echo "  Tentatives de connexion: $LOGIN_ATTEMPTS"
echo "  Connexions réussies: $SUCCESSFUL_LOGINS"
echo "  Échecs de connexion: $FAILED_LOGINS"

if [ $ERROR_COUNT -gt 0 ]; then
    echo ""
    echo "🚨 ATTENTION: Des erreurs ont été détectées dans les logs!"
fi

echo ""
echo "=== Fin de l'analyse ===" 
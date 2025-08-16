#!/usr/bin/env python3
"""
Test direct de la connexion à Ollama
"""

import requests
import json

def test_ollama_direct():
    """Test direct de la connexion à Ollama"""
    print("🧠 Test direct de la connexion à Ollama")
    print("=" * 50)
    
    # Test 1: Vérifier les modèles disponibles
    print("\n1. Vérification des modèles disponibles:")
    try:
        response = requests.get("http://localhost:11434/api/tags", timeout=10)
        if response.status_code == 200:
            models = response.json().get("models", [])
            print(f"   Modèles disponibles: {len(models)}")
            for model in models:
                print(f"      - {model.get('name', 'Unknown')}")
        else:
            print(f"   Erreur: {response.status_code}")
    except Exception as e:
        print(f"   Erreur de connexion: {str(e)}")
    
    # Test 2: Test avec le modèle llama2:7b
    print("\n2. Test avec le modèle llama2:7b:")
    try:
        response = requests.post(
            "http://localhost:11434/api/generate",
            json={
                "model": "llama2:7b",
                "prompt": "Réponds simplement 'OK' si tu reçois ce message.",
                "stream": False
            },
            timeout=120  # 2 minutes pour le modèle lourd
        )
        if response.status_code == 200:
            result = response.json()
            print(f"   Réponse reçue: {result.get('response', 'Pas de réponse')[:100]}...")
        else:
            print(f"   Erreur: {response.status_code} - {response.text}")
    except Exception as e:
        print(f"   Erreur de connexion: {str(e)}")
    
    # Test 3: Test avec le modèle llama2:latest
    print("\n3. Test avec le modèle llama2:latest:")
    try:
        response = requests.post(
            "http://localhost:11434/api/generate",
            json={
                "model": "llama2:latest",
                "prompt": "Réponds simplement 'OK' si tu reçois ce message.",
                "stream": False
            },
            timeout=30
        )
        if response.status_code == 200:
            result = response.json()
            print(f"   Réponse reçue: {result.get('response', 'Pas de réponse')[:100]}...")
        else:
            print(f"   Erreur: {response.status_code} - {response.text}")
    except Exception as e:
        print(f"   Erreur de connexion: {str(e)}")

if __name__ == "__main__":
    test_ollama_direct() 
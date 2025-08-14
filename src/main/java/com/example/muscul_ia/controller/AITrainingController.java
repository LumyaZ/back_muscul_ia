package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.ExternalAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for AI-powered training program generation.
 * Contrôleur pour la génération de programmes d'entraînement avec l'IA.
 */
@RestController
@RequestMapping("/api/ai-training")
@Tag(name = "AI Training Programs", description = "Endpoints for AI-generated training programs")
@CrossOrigin(origins = "*")
public class AITrainingController {
    
    @Autowired
    private ExternalAIService externalAIService;
    
    /**
     * Generate a personalized training program using AI.
     * Générer un programme d'entraînement personnalisé avec l'IA.
     */
    @PostMapping("/generate")
    @Operation(summary = "Générer un programme avec l'IA", 
               description = "Génère un programme d'entraînement personnalisé basé sur le profil utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Programme généré avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Profil utilisateur non trouvé"),
        @ApiResponse(responseCode = "500", description = "Erreur du service IA")
    })
    public ResponseEntity<TrainingProgramDto> generateProgramWithAI(
            @Parameter(description = "ID de l'utilisateur") @RequestParam Long userId) {
        try {
            TrainingProgramDto program = externalAIService.generateProgramWithAI(userId);
            return ResponseEntity.ok(program);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    
    /**
     * Test the connection with the AI service.
     * Tester la connexion avec le service IA.
     */
    @PostMapping("/test-connection")
    @Operation(summary = "Tester la connexion IA", 
               description = "Teste la connexion avec le service IA externe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connexion réussie"),
        @ApiResponse(responseCode = "500", description = "Erreur de connexion")
    })
    public ResponseEntity<Map<String, Object>> testAIConnection() {
        try {
            boolean isConnected = externalAIService.testAIConnection();
            Map<String, Object> response = new HashMap<>();
            response.put("connected", isConnected);
            response.put("message", isConnected ? "Connexion IA réussie" : "Échec de connexion IA");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("connected", false);
            response.put("message", "Erreur de connexion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
    
    /**
     * Get AI service status and health information.
     * Obtenir le statut et les informations de santé du service IA.
     */
    @GetMapping("/status")
    @Operation(summary = "Statut du service IA", 
               description = "Récupère les informations de statut du service IA")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statut récupéré"),
        @ApiResponse(responseCode = "500", description = "Erreur de statut")
    })
    public ResponseEntity<Map<String, Object>> getAIStatus() {
        try {
            boolean isConnected = externalAIService.testAIConnection();
            Map<String, Object> status = new HashMap<>();
            status.put("service", "ai-training");
            status.put("status", isConnected ? "healthy" : "unhealthy");
            status.put("ai_connected", isConnected);
            status.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            Map<String, Object> status = new HashMap<>();
            status.put("service", "ai-training");
            status.put("status", "error");
            status.put("ai_connected", false);
            status.put("error", e.getMessage());
            status.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(status);
        }
    }
} 
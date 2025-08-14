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
     */
    @PostMapping("/generate")
    @Operation(summary = "Generate program with AI", 
               description = "Generates a personalized training program based on user profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Program generated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "404", description = "User profile not found"),
        @ApiResponse(responseCode = "500", description = "AI service error")
    })
    public ResponseEntity<TrainingProgramDto> generateProgramWithAI(
            @Parameter(description = "User ID") @RequestParam Long userId) {
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
     */
    @PostMapping("/test-connection")
    @Operation(summary = "Test AI connection", 
               description = "Tests the connection with the external AI service")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Connection successful"),
        @ApiResponse(responseCode = "500", description = "Connection error")
    })
    public ResponseEntity<Map<String, Object>> testAIConnection() {
        try {
            boolean isConnected = externalAIService.testAIConnection();
            Map<String, Object> response = new HashMap<>();
            response.put("connected", isConnected);
            response.put("message", isConnected ? "AI connection successful" : "AI connection failed");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("connected", false);
            response.put("message", "Connection error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
    
    /**
     * Get AI service status and health information.
     */
    @GetMapping("/status")
    @Operation(summary = "AI service status", 
               description = "Retrieves AI service status information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status retrieved"),
        @ApiResponse(responseCode = "500", description = "Status error")
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
package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.service.ProgramExerciseService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Program exercise controller for managing program-exercise relationships.
 * Contrôleur de programme d'exercices pour gérer les relations programme-exercice.
 */
@RestController
@RequestMapping("/api/program-exercises")
@Tag(name = "Program Exercises", description = "Program exercise management endpoints")
@CrossOrigin(origins = "*")
public class ProgramExerciseController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProgramExerciseController.class);
    
    @Autowired
    private ProgramExerciseService programExerciseService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Get exercises by program ID.
     * Récupérer les exercices par ID de programme.
     */
    @GetMapping("/program/{programId}")
    @Operation(summary = "Get exercises by program ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<List<ProgramExerciseDto>> getExercisesByProgramId(
            @PathVariable Long programId,
            Authentication authentication) {
        try {
            userService.getCurrentUser(authentication);
            logger.info("Retrieving exercises for program: {}", programId);
            
            List<ProgramExerciseDto> exercises = programExerciseService.getExercisesByProgramId(programId);
            return ResponseEntity.ok(exercises);
        } catch (Exception e) {
            logger.error("Failed to retrieve exercises for program {}: {}", programId, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    
    /**
     * Get a program exercise by ID.
     * Récupérer un exercice de programme par ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get program exercise by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Program exercise found"),
        @ApiResponse(responseCode = "404", description = "Program exercise not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ProgramExerciseDto> getProgramExerciseById(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            userService.getCurrentUser(authentication);
            logger.info("Retrieving program exercise by ID: {}", id);
            
            return programExerciseService.getProgramExerciseById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to retrieve program exercise {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    
    /**
     * Add an exercise to a program.
     * Ajouter un exercice à un programme.
     */
    @PostMapping("/program/{programId}")
    @Operation(summary = "Add exercise to program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Exercise added to program successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ProgramExerciseDto> addExerciseToProgram(
            @PathVariable Long programId,
            @RequestBody CreateProgramExerciseRequest request,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur est authentifié
            userService.getCurrentUser(authentication);
            logger.info("Adding exercise to program: {}", programId);
            
            ProgramExerciseDto addedExercise = programExerciseService.addExerciseToProgram(programId, request);
            logger.info("Exercise added to program successfully: {}", addedExercise.getId());
            return ResponseEntity.ok(addedExercise);
        } catch (RuntimeException e) {
            logger.error("Failed to add exercise to program {}: {}", programId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Failed to add exercise to program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
} 
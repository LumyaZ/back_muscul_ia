package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingInfoService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/training-info")
@Tag(name = "Training Information", description = "Endpoints for managing training information")
@CrossOrigin(origins = "*")
public class TrainingInfoController {

    @Autowired
    private TrainingInfoService trainingInfoService;

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create training info", description = "Create training information for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Training info created successfully",
            content = @Content(schema = @Schema(implementation = TrainingInfoDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "409", description = "Training info already exists for this user")
    })
    public ResponseEntity<TrainingInfoDto> createTrainingInfo(@Valid @RequestBody CreateTrainingInfoRequest request,
                                                             Authentication authentication) {
        System.out.println("=== TRAINING INFO CONTROLLER: CREATE ===");
        System.out.println("Request: " + request);
        
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto trainingInfo = trainingInfoService.createTrainingInfo(user, request);
        
        System.out.println("Training info created: " + trainingInfo.getId() + " for user " + trainingInfo.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingInfo);
    }

    @GetMapping
    @Operation(summary = "Get training info", description = "Get training information for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training info retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingInfoDto.class))),
        @ApiResponse(responseCode = "404", description = "Training info not found")
    })
    public ResponseEntity<TrainingInfoDto> getTrainingInfo(Authentication authentication) {
        System.out.println("=== TRAINING INFO CONTROLLER: GET ===");
        
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto trainingInfo = trainingInfoService.getTrainingInfoByUser(user);
        
        System.out.println("Training info retrieved: " + trainingInfo.getId() + " for user " + trainingInfo.getUserId());
        return ResponseEntity.ok(trainingInfo);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get training info by user ID", description = "Get training information for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training info retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingInfoDto.class))),
        @ApiResponse(responseCode = "404", description = "Training info not found")
    })
    public ResponseEntity<TrainingInfoDto> getTrainingInfoByUserId(@PathVariable Long userId) {
        System.out.println("=== TRAINING INFO CONTROLLER: GET BY USER ID ===");
        System.out.println("User ID: " + userId);
        
        TrainingInfoDto trainingInfo = trainingInfoService.getTrainingInfoByUserId(userId);
        
        System.out.println("Training info retrieved: " + trainingInfo.getId() + " for user " + trainingInfo.getUserId());
        return ResponseEntity.ok(trainingInfo);
    }

    @PutMapping
    @Operation(summary = "Update training info", description = "Update training information for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training info updated successfully",
            content = @Content(schema = @Schema(implementation = TrainingInfoDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Training info not found")
    })
    public ResponseEntity<TrainingInfoDto> updateTrainingInfo(@Valid @RequestBody UpdateTrainingInfoRequest request,
                                                             Authentication authentication) {
        System.out.println("=== TRAINING INFO CONTROLLER: UPDATE ===");
        System.out.println("Request: " + request);
        
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto trainingInfo = trainingInfoService.updateTrainingInfo(user, request);
        
        System.out.println("Training info updated: " + trainingInfo.getId() + " for user " + trainingInfo.getUserId());
        return ResponseEntity.ok(trainingInfo);
    }

    @DeleteMapping
    @Operation(summary = "Delete training info", description = "Delete training information for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Training info deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Training info not found")
    })
    public ResponseEntity<Void> deleteTrainingInfo(Authentication authentication) {
        System.out.println("=== TRAINING INFO CONTROLLER: DELETE ===");
        
        User user = userService.getCurrentUser(authentication);
        trainingInfoService.deleteTrainingInfo(user);
        
        System.out.println("Training info deleted for user " + user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    @Operation(summary = "Check if training info exists", description = "Check if training information exists for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsTrainingInfo(Authentication authentication) {
        System.out.println("=== TRAINING INFO CONTROLLER: EXISTS ===");
        
        User user = userService.getCurrentUser(authentication);
        boolean exists = trainingInfoService.existsByUser(user);
        
        System.out.println("Training info exists for user " + user.getId() + ": " + exists);
        return ResponseEntity.ok(exists);
    }
} 
package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.service.ProgramExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program-exercises")
@CrossOrigin(origins = "*")
public class ProgramExerciseController {
    
    @Autowired
    private ProgramExerciseService programExerciseService;
    
    // Récupérer tous les exercices d'un programme par son ID
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ProgramExerciseDto>> getExercisesByProgramId(@PathVariable Long programId) {
        List<ProgramExerciseDto> exercises = programExerciseService.getExercisesByProgramId(programId);
        return ResponseEntity.ok(exercises);
    }
    
    // Récupérer un exercice de programme par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ProgramExerciseDto> getProgramExerciseById(@PathVariable Long id) {
        return programExerciseService.getProgramExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 
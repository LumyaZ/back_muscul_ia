package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.service.impl.TrainingProgramServiceImpl;
import com.example.muscul_ia.service.UserTrainingProgramService;
import com.example.muscul_ia.service.ExerciseService;
import com.example.muscul_ia.service.ProgramExerciseService;
import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

/**
 * Service pour l'intégration avec le service IA externe.
 * Service pour l'intégration avec le service IA externe.
 */
@Service
public class ExternalAIService {
    
    @Value("${ai.service.url:http://localhost:8000}")
    private String aiServiceUrl;
    
    @Autowired
    @Qualifier("aiRestTemplate")
    private RestTemplate restTemplate;
    
    @Autowired
    private UserProfileService userProfileService;
    
    @Autowired
    private TrainingInfoService trainingInfoService;
    
    @Autowired
    private TrainingProgramServiceImpl trainingProgramService;
    
    @Autowired
    private UserTrainingProgramService userTrainingProgramService;
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private ProgramExerciseService programExerciseService;
    
    /**
     * Génère un programme d'entraînement personnalisé avec l'IA.
     * Génère un programme d'entraînement personnalisé avec l'IA.
     * 
     * @param userId ID de l'utilisateur
     * @return TrainingProgramDto Programme généré
     * @throws RuntimeException si erreur lors de la génération
     */
    public TrainingProgramDto generateProgramWithAI(Long userId) {
        try {
            UserProfileDto userProfileDto = userProfileService.getProfileByUserId(userId);
            TrainingInfoDto trainingInfoDto = trainingInfoService.getTrainingInfoByUserId(userId);
            
            if (userProfileDto == null || trainingInfoDto == null) {
                throw new RuntimeException("Profil utilisateur ou informations d'entraînement non trouvés");
            }
            
            Map<String, Object> userData = buildUserDataForAI(userProfileDto, trainingInfoDto);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                aiServiceUrl + "/generate-training-program",
                userData,
                Map.class
            );
            
            if (response.getBody() == null) {
                throw new RuntimeException("Réponse vide du service IA");
            }
            
            TrainingProgramDto result = createTrainingProgramFromAIResponse(response.getBody(), userId);
            
            return result;
            
        } catch (HttpClientErrorException e) {
            System.err.println("Erreur HTTP: " + e.getMessage());
            throw new RuntimeException("Erreur HTTP lors de l'appel au service IA: " + e.getMessage());
        } catch (ResourceAccessException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            throw new RuntimeException("Impossible de se connecter au service IA. Vérifiez qu'il est démarré sur " + aiServiceUrl);
        } catch (Exception e) {
            System.err.println("Erreur générale: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la génération du programme: " + e.getMessage());
        }
    }
    
    /**
     * Teste la connexion avec le service IA.
     * Teste la connexion avec le service IA.
     * 
     * @return true si la connexion fonctionne
     */
    public boolean testAIConnection() {
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                aiServiceUrl + "/test-ai-connection",
                null,
                Map.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Construit les données utilisateur pour l'IA.
     * Construit les données utilisateur pour l'IA.
     * 
     * @param userProfileDto Profil utilisateur DTO
     * @param trainingInfoDto Informations d'entraînement DTO
     * @return Map des données formatées
     */
    private Map<String, Object> buildUserDataForAI(UserProfileDto userProfileDto, TrainingInfoDto trainingInfoDto) {
        Map<String, Object> userData = new HashMap<>();
        
        userData.put("age", userProfileDto.getAge());
        userData.put("gender", trainingInfoDto.getGender());
        userData.put("weight", trainingInfoDto.getWeight());
        userData.put("height", trainingInfoDto.getHeight());
        userData.put("experience_level", trainingInfoDto.getExperienceLevel());
        userData.put("main_goal", trainingInfoDto.getMainGoal());
        userData.put("session_frequency", trainingInfoDto.getSessionFrequency());
        userData.put("session_duration", trainingInfoDto.getSessionDuration());
        userData.put("equipment", trainingInfoDto.getEquipment());
        userData.put("training_preference", trainingInfoDto.getTrainingPreference());
        
        if (trainingInfoDto.getBodyFatPercentage() != null) {
            userData.put("body_fat_percentage", trainingInfoDto.getBodyFatPercentage());
        }
        if (userProfileDto.getFirstName() != null) {
            userData.put("first_name", userProfileDto.getFirstName());
        }
        if (userProfileDto.getLastName() != null) {
            userData.put("last_name", userProfileDto.getLastName());
        }
        if (userProfileDto.getPhoneNumber() != null) {
            userData.put("phone_number", userProfileDto.getPhoneNumber());
        }
        
        return userData;
    }
    
    /**
     * Crée un programme d'entraînement à partir de la réponse IA.
     * Crée un programme d'entraînement à partir de la réponse IA.
     * 
     * @param aiResponse Réponse du service IA
     * @param userId ID de l'utilisateur
     * @return TrainingProgramDto Programme créé
     */
    private TrainingProgramDto createTrainingProgramFromAIResponse(Map<String, Object> aiResponse, Long userId) {
        try {
            CreateTrainingProgramRequest request = new CreateTrainingProgramRequest();
            
            request.setName((String) aiResponse.get("name"));
            request.setDescription((String) aiResponse.get("description"));
            request.setCategory((String) aiResponse.get("category"));
            request.setDifficultyLevel((String) aiResponse.get("difficulty_level"));
            
            String targetAudience = (String) aiResponse.get("target_audience");
            if (targetAudience == null || targetAudience.isEmpty()) {
                targetAudience = "Niveau " + request.getDifficultyLevel() + " - " + userProfileService.getProfileByUserId(userId).getAge() + " ans";
            }
            request.setTargetAudience(targetAudience);
            
            if (aiResponse.get("duration_weeks") instanceof Number) {
                request.setDurationWeeks(((Number) aiResponse.get("duration_weeks")).intValue());
            }
            if (aiResponse.get("sessions_per_week") instanceof Number) {
                request.setSessionsPerWeek(((Number) aiResponse.get("sessions_per_week")).intValue());
            }
            if (aiResponse.get("estimated_duration_minutes") instanceof Number) {
                request.setEstimatedDurationMinutes(((Number) aiResponse.get("estimated_duration_minutes")).intValue());
            }
            
            request.setEquipmentRequired((String) aiResponse.get("equipment_required"));
            
            TrainingProgramDto result = trainingProgramService.createTrainingProgram(request, userId);
            
            userTrainingProgramService.subscribeUserToProgram(userId, result.getId());
            
            processExercisesFromAIResponse(aiResponse, result.getId());
            
            return result;
            
        } catch (Exception e) {
            System.err.println("=== ERREUR LORS DE LA CRÉATION ===");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Type: " + e.getClass().getSimpleName());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création du programme à partir de la réponse IA: " + e.getMessage());
        }
    }
    
    /**
     * Traite les exercices de la réponse IA et les lie au programme.
     * Traite les exercices de la réponse IA et les lie au programme.
     * 
     * @param aiResponse Réponse du service IA
     * @param programId ID du programme créé
     */
    private void processExercisesFromAIResponse(Map<String, Object> aiResponse, Long programId) {
        try {
            Object exercisesObj = aiResponse.get("exercises");
            if (exercisesObj == null) {
                return;
            }
            
            if (!(exercisesObj instanceof List)) {
                return;
            }
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> exercises = (List<Map<String, Object>>) exercisesObj;
            
            for (int i = 0; i < exercises.size(); i++) {
                Map<String, Object> exerciseData = exercises.get(i);
                
                ExerciseDto exercise = createExerciseFromAIResponse(exerciseData);
                
                linkExerciseToProgram(exercise.getId(), programId, exerciseData);
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des exercices: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Crée un exercice à partir des données IA.
     * Crée un exercice à partir des données IA.
     * 
     * @param exerciseData Données de l'exercice depuis l'IA
     * @return ExerciseDto Exercice créé
     */
    private ExerciseDto createExerciseFromAIResponse(Map<String, Object> exerciseData) {
        CreateExerciseRequest request = new CreateExerciseRequest();
        
        request.setName((String) exerciseData.get("name"));
        request.setDescription((String) exerciseData.get("description"));
        request.setCategory("Musculation");
        request.setMuscleGroup((String) exerciseData.get("muscle_group"));
        request.setEquipmentNeeded((String) exerciseData.get("equipment"));
        request.setDifficultyLevel((String) exerciseData.get("difficulty_level")); 
        
        return exerciseService.createExercise(request);
    }
    
    /**
     * Lie un exercice à un programme avec les paramètres spécifiés.
     * Lie un exercice à un programme avec les paramètres spécifiés.
     * 
     * @param exerciseId ID de l'exercice
     * @param programId ID du programme
     * @param exerciseData Données de l'exercice depuis l'IA
     */
    private void linkExerciseToProgram(Long exerciseId, Long programId, Map<String, Object> exerciseData) {
        CreateProgramExerciseRequest request = new CreateProgramExerciseRequest();
        
        request.setExerciseId(exerciseId);
        
        Object setsObj = exerciseData.get("sets_count");
        if (setsObj instanceof Number) {
            request.setSetsCount(((Number) setsObj).intValue());
        } else {
            request.setSetsCount(3); 
        }
        
        Object repsObj = exerciseData.get("reps_count");
        if (repsObj instanceof String) {
            String repsStr = (String) repsObj;
            try {
                String[] parts = repsStr.split("-");
                int reps = Integer.parseInt(parts[0].trim());
                request.setRepsCount(reps);
            } catch (Exception e) {
                request.setRepsCount(10); 
            }
        } else if (repsObj instanceof Number) {
            request.setRepsCount(((Number) repsObj).intValue());
        } else {
            request.setRepsCount(10); 
        }
        
        Object restObj = exerciseData.get("rest");
        if (restObj instanceof String) {
            String restStr = (String) restObj;
            try {
                String seconds = restStr.replaceAll("[^0-9]", "");
                int restSeconds = Integer.parseInt(seconds);
                request.setRestDurationSeconds(restSeconds);
            } catch (Exception e) {
                request.setRestDurationSeconds(90); 
            }
        } else {
            request.setRestDurationSeconds(90); 
        }
        
        request.setNotes((String) exerciseData.get("notes"));
        
        programExerciseService.addExerciseToProgram(programId, request);
    }
}
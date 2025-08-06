package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.service.ExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ExerciseController Tests")
class ExerciseControllerTest {

    private MockMvc mockMvc;
    private ExerciseService exerciseService;
    private ObjectMapper objectMapper;

    private ExerciseDto exerciseDto;
    private CreateExerciseRequest createRequest;
    private List<ExerciseDto> exerciseList;

    /**
     * Set up test data before each test.
     * Configure les données de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        exerciseService = mock(ExerciseService.class);
        
        ExerciseController controller = new ExerciseController();
        try {
            java.lang.reflect.Field exerciseServiceField = ExerciseController.class.getDeclaredField("exerciseService");
            exerciseServiceField.setAccessible(true);
            exerciseServiceField.set(controller, exerciseService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        exerciseDto = new ExerciseDto();
        exerciseDto.setId(1L);
        exerciseDto.setName("Pompes");
        exerciseDto.setDescription("Exercice de musculation pour les pectoraux");
        exerciseDto.setCategory("Musculation");
        exerciseDto.setMuscleGroup("Pectoraux");
        exerciseDto.setEquipmentNeeded("Poids du corps");
        exerciseDto.setDifficultyLevel("Débutant");
        exerciseDto.setIsActive(true);
        exerciseDto.setCreatedAt(LocalDateTime.now());

        createRequest = new CreateExerciseRequest();
        createRequest.setName("Squats");
        createRequest.setDescription("Exercice pour les jambes");
        createRequest.setCategory("Musculation");
        createRequest.setMuscleGroup("Jambes");
        createRequest.setEquipmentNeeded("Poids du corps");
        createRequest.setDifficultyLevel("Débutant");

        exerciseList = Arrays.asList(exerciseDto);
    }

    /**
     * Test successful exercise creation.
     * Teste la création réussie d'un exercice.
     */
    @Test
    @DisplayName("Should create exercise successfully")
    void shouldCreateExerciseSuccessfully() throws Exception {
        when(exerciseService.createExercise(any(CreateExerciseRequest.class))).thenReturn(exerciseDto);

        mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$.description").value(exerciseDto.getDescription()))
                .andExpect(jsonPath("$.category").value(exerciseDto.getCategory()))
                .andExpect(jsonPath("$.muscleGroup").value(exerciseDto.getMuscleGroup()))
                .andExpect(jsonPath("$.equipmentNeeded").value(exerciseDto.getEquipmentNeeded()))
                .andExpect(jsonPath("$.difficultyLevel").value(exerciseDto.getDifficultyLevel()))
                .andExpect(jsonPath("$.isActive").value(exerciseDto.getIsActive()));

        verify(exerciseService, times(1)).createExercise(any(CreateExerciseRequest.class));
    }

    /**
     * Test exercise creation failure.
     * Teste l'échec de la création d'un exercice.
     */
    @Test
    @DisplayName("Should return bad request when creating exercise fails")
    void shouldReturnBadRequestWhenCreatingExerciseFails() throws Exception {
        when(exerciseService.createExercise(any(CreateExerciseRequest.class)))
                .thenThrow(new RuntimeException("Exercise creation failed"));

        mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isBadRequest());

        verify(exerciseService, times(1)).createExercise(any(CreateExerciseRequest.class));
    }

    /**
     * Test successful retrieval of all exercises.
     * Teste la récupération réussie de tous les exercices.
     */
    @Test
    @DisplayName("Should get all exercises successfully")
    void shouldGetAllExercisesSuccessfully() throws Exception {
        when(exerciseService.getAllActiveExercises()).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].description").value(exerciseDto.getDescription()))
                .andExpect(jsonPath("$[0].category").value(exerciseDto.getCategory()))
                .andExpect(jsonPath("$[0].muscleGroup").value(exerciseDto.getMuscleGroup()))
                .andExpect(jsonPath("$[0].equipmentNeeded").value(exerciseDto.getEquipmentNeeded()))
                .andExpect(jsonPath("$[0].difficultyLevel").value(exerciseDto.getDifficultyLevel()))
                .andExpect(jsonPath("$[0].isActive").value(exerciseDto.getIsActive()));

        verify(exerciseService, times(1)).getAllActiveExercises();
    }

    /**
     * Test successful retrieval of exercise by ID.
     * Teste la récupération réussie d'un exercice par ID.
     */
    @Test
    @DisplayName("Should get exercise by id when exists")
    void shouldGetExerciseByIdWhenExists() throws Exception {
        when(exerciseService.getExerciseById(1L)).thenReturn(Optional.of(exerciseDto));

        mockMvc.perform(get("/api/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$.description").value(exerciseDto.getDescription()))
                .andExpect(jsonPath("$.category").value(exerciseDto.getCategory()))
                .andExpect(jsonPath("$.muscleGroup").value(exerciseDto.getMuscleGroup()))
                .andExpect(jsonPath("$.equipmentNeeded").value(exerciseDto.getEquipmentNeeded()))
                .andExpect(jsonPath("$.difficultyLevel").value(exerciseDto.getDifficultyLevel()))
                .andExpect(jsonPath("$.isActive").value(exerciseDto.getIsActive()));

        verify(exerciseService, times(1)).getExerciseById(1L);
    }

    /**
     * Test exercise not found by ID.
     * Teste le cas où l'exercice n'est pas trouvé par ID.
     */
    @Test
    @DisplayName("Should return not found when exercise does not exist")
    void shouldReturnNotFoundWhenExerciseDoesNotExist() throws Exception {
        when(exerciseService.getExerciseById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/exercises/999"))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).getExerciseById(999L);
    }

    /**
     * Test successful exercise update.
     * Teste la mise à jour réussie d'un exercice.
     */
    @Test
    @DisplayName("Should update exercise successfully")
    void shouldUpdateExerciseSuccessfully() throws Exception {
        when(exerciseService.updateExercise(eq(1L), any(CreateExerciseRequest.class)))
                .thenReturn(exerciseDto);

        mockMvc.perform(put("/api/exercises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$.description").value(exerciseDto.getDescription()))
                .andExpect(jsonPath("$.category").value(exerciseDto.getCategory()))
                .andExpect(jsonPath("$.muscleGroup").value(exerciseDto.getMuscleGroup()))
                .andExpect(jsonPath("$.equipmentNeeded").value(exerciseDto.getEquipmentNeeded()))
                .andExpect(jsonPath("$.difficultyLevel").value(exerciseDto.getDifficultyLevel()))
                .andExpect(jsonPath("$.isActive").value(exerciseDto.getIsActive()));

        verify(exerciseService, times(1)).updateExercise(eq(1L), any(CreateExerciseRequest.class));
    }

    /**
     * Test exercise update when exercise does not exist.
     * Teste la mise à jour d'un exercice qui n'existe pas.
     */
    @Test
    @DisplayName("Should return not found when updating non-existent exercise")
    void shouldReturnNotFoundWhenUpdatingNonExistentExercise() throws Exception {
        when(exerciseService.updateExercise(eq(999L), any(CreateExerciseRequest.class)))
                .thenThrow(new RuntimeException("Exercise not found"));

        mockMvc.perform(put("/api/exercises/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).updateExercise(eq(999L), any(CreateExerciseRequest.class));
    }

    /**
     * Test successful exercise deletion.
     * Teste la suppression réussie d'un exercice.
     */
    @Test
    @DisplayName("Should delete exercise successfully")
    void shouldDeleteExerciseSuccessfully() throws Exception {
        doNothing().when(exerciseService).deleteExercise(1L);

        mockMvc.perform(delete("/api/exercises/1"))
                .andExpect(status().isNoContent());

        verify(exerciseService, times(1)).deleteExercise(1L);
    }

    /**
     * Test exercise deletion when exercise does not exist.
     * Teste la suppression d'un exercice qui n'existe pas.
     */
    @Test
    @DisplayName("Should return not found when deleting non-existent exercise")
    void shouldReturnNotFoundWhenDeletingNonExistentExercise() throws Exception {
        doThrow(new RuntimeException("Exercise not found")).when(exerciseService).deleteExercise(999L);

        mockMvc.perform(delete("/api/exercises/999"))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).deleteExercise(999L);
    }

    /**
     * Test successful search exercises by name.
     * Teste la recherche réussie d'exercices par nom.
     */
    @Test
    @DisplayName("Should search exercises by name successfully")
    void shouldSearchExercisesByNameSuccessfully() throws Exception {
        when(exerciseService.searchExercisesByName("pompes")).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises/search")
                .param("name", "pompes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].description").value(exerciseDto.getDescription()));

        verify(exerciseService, times(1)).searchExercisesByName("pompes");
    }

    /**
     * Test successful retrieval of exercises by category.
     * Teste la récupération réussie d'exercices par catégorie.
     */
    @Test
    @DisplayName("Should get exercises by category successfully")
    void shouldGetExercisesByCategorySuccessfully() throws Exception {
        when(exerciseService.getExercisesByCategory("Musculation")).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises/category")
                .param("category", "Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].category").value(exerciseDto.getCategory()));

        verify(exerciseService, times(1)).getExercisesByCategory("Musculation");
    }

    /**
     * Test successful retrieval of exercises by muscle group.
     * Teste la récupération réussie d'exercices par groupe musculaire.
     */
    @Test
    @DisplayName("Should get exercises by muscle group successfully")
    void shouldGetExercisesByMuscleGroupSuccessfully() throws Exception {
        when(exerciseService.getExercisesByMuscleGroup("Pectoraux")).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises/muscle-group")
                .param("muscleGroup", "Pectoraux"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].muscleGroup").value(exerciseDto.getMuscleGroup()));

        verify(exerciseService, times(1)).getExercisesByMuscleGroup("Pectoraux");
    }

    /**
     * Test successful retrieval of exercises by difficulty level.
     * Teste la récupération réussie d'exercices par niveau de difficulté.
     */
    @Test
    @DisplayName("Should get exercises by difficulty level successfully")
    void shouldGetExercisesByDifficultyLevelSuccessfully() throws Exception {
        when(exerciseService.getExercisesByDifficultyLevel("Débutant")).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises/difficulty")
                .param("difficultyLevel", "Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].difficultyLevel").value(exerciseDto.getDifficultyLevel()));

        verify(exerciseService, times(1)).getExercisesByDifficultyLevel("Débutant");
    }

    /**
     * Test successful retrieval of exercises by equipment.
     * Teste la récupération réussie d'exercices par équipement.
     */
    @Test
    @DisplayName("Should get exercises by equipment successfully")
    void shouldGetExercisesByEquipmentSuccessfully() throws Exception {
        when(exerciseService.getExercisesByEquipment("Poids du corps")).thenReturn(exerciseList);

        mockMvc.perform(get("/api/exercises/equipment")
                .param("equipment", "Poids du corps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].equipmentNeeded").value(exerciseDto.getEquipmentNeeded()));

        verify(exerciseService, times(1)).getExercisesByEquipment("Poids du corps");
    }
} 
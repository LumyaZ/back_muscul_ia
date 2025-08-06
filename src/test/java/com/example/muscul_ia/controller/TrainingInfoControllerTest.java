package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.entity.TrainingInfo;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.enums.Equipment;
import com.example.muscul_ia.enums.ExperienceLevel;
import com.example.muscul_ia.enums.Gender;
import com.example.muscul_ia.enums.MainGoal;
import com.example.muscul_ia.enums.SessionDuration;
import com.example.muscul_ia.enums.SessionFrequency;
import com.example.muscul_ia.enums.TrainingPreference;
import com.example.muscul_ia.service.TrainingInfoService;
import com.example.muscul_ia.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TrainingInfoController Tests")
class TrainingInfoControllerTest {

    private MockMvc mockMvc;
    private TrainingInfoService trainingInfoService;
    private UserService userService;
    private Authentication authentication;
    private ObjectMapper objectMapper;

    private User testUser;
    private TrainingInfo testTrainingInfo;
    private TrainingInfoDto testTrainingInfoDto;
    private CreateTrainingInfoRequest createRequest;
    private UpdateTrainingInfoRequest updateRequest;

    @BeforeEach
    void setUp() {
        trainingInfoService = mock(TrainingInfoService.class);
        userService = mock(UserService.class);
        authentication = mock(Authentication.class);
        
        TrainingInfoController controller = new TrainingInfoController();
        try {
            java.lang.reflect.Field trainingInfoServiceField = TrainingInfoController.class.getDeclaredField("trainingInfoService");
            trainingInfoServiceField.setAccessible(true);
            trainingInfoServiceField.set(controller, trainingInfoService);
            
            java.lang.reflect.Field userServiceField = TrainingInfoController.class.getDeclaredField("userService");
            userServiceField.setAccessible(true);
            userServiceField.set(controller, userService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testTrainingInfo = new TrainingInfo();
        testTrainingInfo.setId(1L);
        testTrainingInfo.setUser(testUser);
        testTrainingInfo.setGender(Gender.MALE);
        testTrainingInfo.setWeight(75.0);
        testTrainingInfo.setHeight(180.0);
        testTrainingInfo.setBodyFatPercentage(15.0);
        testTrainingInfo.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        testTrainingInfo.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        testTrainingInfo.setSessionDuration(SessionDuration.MEDIUM);
        testTrainingInfo.setMainGoal(MainGoal.MUSCLE_GAIN);
        testTrainingInfo.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        testTrainingInfo.setEquipment(Equipment.BASIC);

        testTrainingInfoDto = new TrainingInfoDto(testTrainingInfo);

        createRequest = new CreateTrainingInfoRequest();
        createRequest.setGender(Gender.MALE);
        createRequest.setWeight(75.0);
        createRequest.setHeight(180.0);
        createRequest.setBodyFatPercentage(15.0);
        createRequest.setExperienceLevel(ExperienceLevel.INTERMEDIATE);
        createRequest.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
        createRequest.setSessionDuration(SessionDuration.MEDIUM);
        createRequest.setMainGoal(MainGoal.MUSCLE_GAIN);
        createRequest.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        createRequest.setEquipment(Equipment.BASIC);

        updateRequest = new UpdateTrainingInfoRequest();
        updateRequest.setGender(Gender.MALE);
        updateRequest.setWeight(80.0);
        updateRequest.setHeight(180.0);
        updateRequest.setBodyFatPercentage(12.0);
        updateRequest.setExperienceLevel(ExperienceLevel.ADVANCED);
        updateRequest.setSessionFrequency(SessionFrequency.FIVE_TO_SIX);
        updateRequest.setSessionDuration(SessionDuration.LONG);
        updateRequest.setMainGoal(MainGoal.STRENGTH);
        updateRequest.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
        updateRequest.setEquipment(Equipment.BASIC);
    }

    @Test
    void createTrainingInfo_Success() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        when(trainingInfoService.createTrainingInfo(testUser, createRequest)).thenReturn(testTrainingInfoDto);

        mockMvc.perform(post("/api/training-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()))
                .andExpect(jsonPath("$.gender").value(testTrainingInfoDto.getGender().toString()))
                .andExpect(jsonPath("$.weight").value(testTrainingInfoDto.getWeight()))
                .andExpect(jsonPath("$.height").value(testTrainingInfoDto.getHeight()))
                .andExpect(jsonPath("$.bodyFatPercentage").value(testTrainingInfoDto.getBodyFatPercentage()))
                .andExpect(jsonPath("$.experienceLevel").value(testTrainingInfoDto.getExperienceLevel().toString()))
                .andExpect(jsonPath("$.sessionFrequency").value(testTrainingInfoDto.getSessionFrequency().toString()))
                .andExpect(jsonPath("$.sessionDuration").value(testTrainingInfoDto.getSessionDuration().toString()))
                .andExpect(jsonPath("$.mainGoal").value(testTrainingInfoDto.getMainGoal().toString()))
                .andExpect(jsonPath("$.trainingPreference").value(testTrainingInfoDto.getTrainingPreference().toString()))
                .andExpect(jsonPath("$.equipment").value(testTrainingInfoDto.getEquipment().toString()));

        verify(trainingInfoService, times(1)).createTrainingInfo(testUser, createRequest);
    }

    @Test
    void getMyTrainingInfo_Success() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        when(trainingInfoService.getTrainingInfoByUser(testUser)).thenReturn(testTrainingInfoDto);

        mockMvc.perform(get("/api/training-info/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()))
                .andExpect(jsonPath("$.gender").value(testTrainingInfoDto.getGender().toString()))
                .andExpect(jsonPath("$.weight").value(testTrainingInfoDto.getWeight()))
                .andExpect(jsonPath("$.height").value(testTrainingInfoDto.getHeight()))
                .andExpect(jsonPath("$.bodyFatPercentage").value(testTrainingInfoDto.getBodyFatPercentage()))
                .andExpect(jsonPath("$.experienceLevel").value(testTrainingInfoDto.getExperienceLevel().toString()))
                .andExpect(jsonPath("$.sessionFrequency").value(testTrainingInfoDto.getSessionFrequency().toString()))
                .andExpect(jsonPath("$.sessionDuration").value(testTrainingInfoDto.getSessionDuration().toString()))
                .andExpect(jsonPath("$.mainGoal").value(testTrainingInfoDto.getMainGoal().toString()))
                .andExpect(jsonPath("$.trainingPreference").value(testTrainingInfoDto.getTrainingPreference().toString()))
                .andExpect(jsonPath("$.equipment").value(testTrainingInfoDto.getEquipment().toString()));

        verify(trainingInfoService, times(1)).getTrainingInfoByUser(testUser);
    }

    @Test
    void getTrainingInfoByUserId_Success() throws Exception {
        when(trainingInfoService.getTrainingInfoByUserId(1L)).thenReturn(testTrainingInfoDto);

        mockMvc.perform(get("/api/training-info/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()))
                .andExpect(jsonPath("$.gender").value(testTrainingInfoDto.getGender().toString()))
                .andExpect(jsonPath("$.weight").value(testTrainingInfoDto.getWeight()))
                .andExpect(jsonPath("$.height").value(testTrainingInfoDto.getHeight()))
                .andExpect(jsonPath("$.bodyFatPercentage").value(testTrainingInfoDto.getBodyFatPercentage()))
                .andExpect(jsonPath("$.experienceLevel").value(testTrainingInfoDto.getExperienceLevel().toString()))
                .andExpect(jsonPath("$.sessionFrequency").value(testTrainingInfoDto.getSessionFrequency().toString()))
                .andExpect(jsonPath("$.sessionDuration").value(testTrainingInfoDto.getSessionDuration().toString()))
                .andExpect(jsonPath("$.mainGoal").value(testTrainingInfoDto.getMainGoal().toString()))
                .andExpect(jsonPath("$.trainingPreference").value(testTrainingInfoDto.getTrainingPreference().toString()))
                .andExpect(jsonPath("$.equipment").value(testTrainingInfoDto.getEquipment().toString()));

        verify(trainingInfoService, times(1)).getTrainingInfoByUserId(1L);
    }

    @Test
    void updateMyTrainingInfo_Success() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        when(trainingInfoService.updateTrainingInfo(testUser, updateRequest)).thenReturn(testTrainingInfoDto);

        mockMvc.perform(put("/api/training-info/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()))
                .andExpect(jsonPath("$.gender").value(testTrainingInfoDto.getGender().toString()))
                .andExpect(jsonPath("$.weight").value(testTrainingInfoDto.getWeight()))
                .andExpect(jsonPath("$.height").value(testTrainingInfoDto.getHeight()))
                .andExpect(jsonPath("$.bodyFatPercentage").value(testTrainingInfoDto.getBodyFatPercentage()))
                .andExpect(jsonPath("$.experienceLevel").value(testTrainingInfoDto.getExperienceLevel().toString()))
                .andExpect(jsonPath("$.sessionFrequency").value(testTrainingInfoDto.getSessionFrequency().toString()))
                .andExpect(jsonPath("$.sessionDuration").value(testTrainingInfoDto.getSessionDuration().toString()))
                .andExpect(jsonPath("$.mainGoal").value(testTrainingInfoDto.getMainGoal().toString()))
                .andExpect(jsonPath("$.trainingPreference").value(testTrainingInfoDto.getTrainingPreference().toString()))
                .andExpect(jsonPath("$.equipment").value(testTrainingInfoDto.getEquipment().toString()));

        verify(trainingInfoService, times(1)).updateTrainingInfo(testUser, updateRequest);
    }

    @Test
    void deleteMyTrainingInfo_Success() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        doNothing().when(trainingInfoService).deleteTrainingInfo(testUser);

        mockMvc.perform(delete("/api/training-info/me"))
                .andExpect(status().isNoContent());

        verify(trainingInfoService, times(1)).deleteTrainingInfo(testUser);
    }

    @Test
    void checkTrainingInfoExists_Success() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        when(trainingInfoService.existsByUser(testUser)).thenReturn(true);

        mockMvc.perform(get("/api/training-info/me/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(trainingInfoService, times(1)).existsByUser(testUser);
    }

    @Test
    void checkTrainingInfoExists_NotExists() throws Exception {
        when(userService.getCurrentUser(authentication)).thenReturn(testUser);
        when(trainingInfoService.existsByUser(testUser)).thenReturn(false);

        mockMvc.perform(get("/api/training-info/me/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(trainingInfoService, times(1)).existsByUser(testUser);
    }
} 
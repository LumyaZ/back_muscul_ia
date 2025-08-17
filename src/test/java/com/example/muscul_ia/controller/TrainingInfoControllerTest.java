package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.enums.*;
import com.example.muscul_ia.service.TrainingInfoService;
import com.example.muscul_ia.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrainingInfoControllerTest {

	private MockMvc mockMvc;
	private TrainingInfoService trainingInfoService;
	private UserService userService;
	private ObjectMapper objectMapper;
	private Authentication authentication;
	
	private User testUser;
	private TrainingInfoDto testTrainingInfoDto;
	private CreateTrainingInfoRequest createRequest;
	private UpdateTrainingInfoRequest updateRequest;

	@BeforeEach
	void setUp() {
		trainingInfoService = mock(TrainingInfoService.class);
		userService = mock(UserService.class);
		authentication = mock(Authentication.class);
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TrainingInfoController controller = new TrainingInfoController();
		try {
			java.lang.reflect.Field trainingInfoServiceField = TrainingInfoController.class.getDeclaredField("trainingInfoService");
			trainingInfoServiceField.setAccessible(true);
			trainingInfoServiceField.set(controller, trainingInfoService);
			
			java.lang.reflect.Field userServiceField = TrainingInfoController.class.getDeclaredField("userService");
			userServiceField.setAccessible(true);
			userServiceField.set(controller, userService);
		} catch (Exception e) {
			throw new RuntimeException("Failed to inject services", e);
		}
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		testUser = new User();
		testUser.setId(1L);
		testUser.setEmail("test@example.com");
		
		testTrainingInfoDto = new TrainingInfoDto();
		testTrainingInfoDto.setId(1L);
		testTrainingInfoDto.setUserId(1L);
		testTrainingInfoDto.setGender(Gender.MALE);
		testTrainingInfoDto.setWeight(75.0);
		testTrainingInfoDto.setHeight(180.0);
		testTrainingInfoDto.setBodyFatPercentage(15.0);
		testTrainingInfoDto.setExperienceLevel(ExperienceLevel.BEGINNER);
		testTrainingInfoDto.setSessionFrequency(SessionFrequency.THREE_TO_FOUR);
		testTrainingInfoDto.setSessionDuration(SessionDuration.MEDIUM);
		testTrainingInfoDto.setMainGoal(MainGoal.WEIGHT_LOSS);
		testTrainingInfoDto.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING);
		testTrainingInfoDto.setEquipment(Equipment.BASIC);
		testTrainingInfoDto.setCreatedAt(LocalDateTime.now());
		testTrainingInfoDto.setUpdatedAt(LocalDateTime.now());
		
		createRequest = new CreateTrainingInfoRequest();
		createRequest.setGender(Gender.MALE.name());
		createRequest.setWeight(75.0);
		createRequest.setHeight(180.0);
		createRequest.setBodyFatPercentage(15.0);
		createRequest.setExperienceLevel(ExperienceLevel.BEGINNER.name());
		createRequest.setSessionFrequency(SessionFrequency.THREE_TO_FOUR.name());
		createRequest.setSessionDuration(SessionDuration.MEDIUM.name());
		createRequest.setMainGoal(MainGoal.WEIGHT_LOSS.name());
		createRequest.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING.name());
		createRequest.setEquipment(Equipment.BASIC.name());
		
		updateRequest = new UpdateTrainingInfoRequest();
		updateRequest.setGender(Gender.MALE.name());
		updateRequest.setWeight(80.0);
		updateRequest.setHeight(180.0);
		updateRequest.setBodyFatPercentage(12.0);
		updateRequest.setExperienceLevel(ExperienceLevel.ADVANCED.name());
		updateRequest.setSessionFrequency(SessionFrequency.FIVE_TO_SIX.name());
		updateRequest.setSessionDuration(SessionDuration.LONG.name());
		updateRequest.setMainGoal(MainGoal.STRENGTH.name());
		updateRequest.setTrainingPreference(TrainingPreference.STRENGTH_TRAINING.name());
		updateRequest.setEquipment(Equipment.BASIC.name());
	}
	
	@Test
	void createTrainingInfo_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(trainingInfoService.createTrainingInfo(any(User.class), any(CreateTrainingInfoRequest.class))).thenReturn(testTrainingInfoDto);
		
		mockMvc.perform(post("/api/training-info").principal(authentication)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()));
		
		verify(trainingInfoService, times(1)).createTrainingInfo(any(User.class), any(CreateTrainingInfoRequest.class));
	}
	
	@Test
	void getMyTrainingInfo_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(trainingInfoService.getTrainingInfoByUser(any(User.class))).thenReturn(testTrainingInfoDto);
		
		mockMvc.perform(get("/api/training-info").principal(authentication))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()));
		
		verify(trainingInfoService, times(1)).getTrainingInfoByUser(any(User.class));
	}
	
	@Test
	void getTrainingInfoByUserId_Success() throws Exception {
		when(trainingInfoService.getTrainingInfoByUserId(1L)).thenReturn(testTrainingInfoDto);
		
		mockMvc.perform(get("/api/training-info/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()));
		
		verify(trainingInfoService, times(1)).getTrainingInfoByUserId(1L);
	}
	
	@Test
	void updateMyTrainingInfo_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(trainingInfoService.updateTrainingInfo(any(User.class), any(UpdateTrainingInfoRequest.class))).thenReturn(testTrainingInfoDto);
		
		mockMvc.perform(put("/api/training-info").principal(authentication)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testTrainingInfoDto.getId()));
		
		verify(trainingInfoService, times(1)).updateTrainingInfo(any(User.class), any(UpdateTrainingInfoRequest.class));
	}
	
	@Test
	void deleteMyTrainingInfo_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		doNothing().when(trainingInfoService).deleteTrainingInfo(any(User.class));
		
		mockMvc.perform(delete("/api/training-info").principal(authentication))
				.andExpect(status().isNoContent());
		
		verify(trainingInfoService, times(1)).deleteTrainingInfo(any(User.class));
	}
	
	@Test
	void checkTrainingInfoExists_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(trainingInfoService.existsByUser(any(User.class))).thenReturn(true);
		
		mockMvc.perform(get("/api/training-info/exists").principal(authentication))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
		
		verify(trainingInfoService, times(1)).existsByUser(any(User.class));
	}
	
	@Test
	void checkTrainingInfoExists_NotExists() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(trainingInfoService.existsByUser(any(User.class))).thenReturn(false);
		
		mockMvc.perform(get("/api/training-info/exists").principal(authentication))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
		
		verify(trainingInfoService, times(1)).existsByUser(any(User.class));
	}
} 
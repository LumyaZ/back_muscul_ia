package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.service.UserProfileService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("UserProfileController Tests")
class UserProfileControllerTest {

	private MockMvc mockMvc;
	private UserProfileService userProfileService;
	private UserService userService;
	private Authentication authentication;
	private ObjectMapper objectMapper;
	
	private User testUser;
	private UserProfile testUserProfile;
	private UserProfileDto testUserProfileDto;
	private CreateUserProfileRequest createRequest;
	private CreateUserProfileWithEmailRequest createWithEmailRequest;
	private UpdateUserProfileRequest updateRequest;
	
	@BeforeEach
	void setUp() {
		userProfileService = mock(UserProfileService.class);
		userService = mock(UserService.class);
		authentication = mock(Authentication.class);
		
		UserProfileController controller = new UserProfileController();
		try {
			java.lang.reflect.Field userProfileServiceField = UserProfileController.class.getDeclaredField("userProfileService");
			userProfileServiceField.setAccessible(true);
			userProfileServiceField.set(controller, userProfileService);
			
			java.lang.reflect.Field userServiceField = UserProfileController.class.getDeclaredField("userService");
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
		
		testUserProfile = new UserProfile();
		testUserProfile.setId(1L);
		testUserProfile.setUser(testUser);
		testUserProfile.setFirstName("John");
		testUserProfile.setLastName("Doe");
		testUserProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
		testUserProfile.setAge(33);
		testUserProfile.setPhoneNumber("+33123456789");
		testUserProfile.setCreatedAt(LocalDateTime.now());
		testUserProfile.setUpdatedAt(LocalDateTime.now());
		
		testUserProfileDto = new UserProfileDto(testUserProfile);
		
		createRequest = new CreateUserProfileRequest();
		createRequest.setFirstName("John");
		createRequest.setLastName("Doe");
		createRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
		createRequest.setPhoneNumber("+33123456789");
		
		createWithEmailRequest = new CreateUserProfileWithEmailRequest();
		createWithEmailRequest.setEmail("test@example.com");
		createWithEmailRequest.setFirstName("John");
		createWithEmailRequest.setLastName("Doe");
		createWithEmailRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
		createWithEmailRequest.setPhoneNumber("+33123456789");
		
		updateRequest = new UpdateUserProfileRequest();
		updateRequest.setFirstName("Jane");
		updateRequest.setLastName("Smith");
		updateRequest.setPhoneNumber("+33987654321");
	}
	
	@Test
	void createProfile_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(userProfileService.createProfile(any(User.class), any(CreateUserProfileRequest.class))).thenReturn(testUserProfileDto);
		
		mockMvc.perform(post("/api/profiles").principal(authentication)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(testUserProfileDto.getId()));
	}
	
	@Test
	void createProfileByEmail_Success() throws Exception {
		when(userProfileService.createProfileByEmail(any(CreateUserProfileWithEmailRequest.class))).thenReturn(testUserProfileDto);
		
		mockMvc.perform(post("/api/profiles/public")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createWithEmailRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(testUserProfileDto.getId()));
	}
	
	@Test
	void getMyProfile_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(userProfileService.getProfileByUser(any(User.class))).thenReturn(testUserProfileDto);
		
		mockMvc.perform(get("/api/profiles/me").principal(authentication))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testUserProfileDto.getId()));
	}
	
	@Test
	void getProfileByUserId_Success() throws Exception {
		when(userProfileService.getProfileByUserId(1L)).thenReturn(testUserProfileDto);
		
		mockMvc.perform(get("/api/profiles/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testUserProfileDto.getId()));
	}
	
	@Test
	void updateMyProfile_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		when(userProfileService.updateProfile(any(User.class), any(UpdateUserProfileRequest.class))).thenReturn(testUserProfileDto);
		
		mockMvc.perform(put("/api/profiles/me").principal(authentication)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(testUserProfileDto.getId()));
	}
	
	@Test
	void deleteMyProfile_Success() throws Exception {
		when(userService.getCurrentUser(any(Authentication.class))).thenReturn(testUser);
		doNothing().when(userProfileService).deleteProfile(any(User.class));
		
		mockMvc.perform(delete("/api/profiles/me").principal(authentication))
				.andExpect(status().isNoContent());
	}
} 
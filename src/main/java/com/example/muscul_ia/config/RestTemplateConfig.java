package com.example.muscul_ia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;

/**
 * Configuration for RestTemplate used for external API calls.
 * Configuration pour RestTemplate utilisé pour les appels API externes.
 */
@Configuration
public class RestTemplateConfig {
	@Value("${http.client.connect-timeout-ms:30000}")
	private int connectTimeoutMs;
	
	@Value("${http.client.read-timeout-ms:60000}")
	private int readTimeoutMs;
	
	@Value("${ai.service.timeout:300000}")
	private int aiServiceTimeoutMs;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(connectTimeoutMs))
				.setReadTimeout(Duration.ofMillis(readTimeoutMs))
				.build();
	}
	
	@Bean
	public RestTemplate aiRestTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeoutMs);
		factory.setReadTimeout(aiServiceTimeoutMs);
		
		return new RestTemplate(factory);
	}
} 
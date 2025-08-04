package com.example.muscul_ia.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for application metrics and monitoring.
 * Configuration des métriques et du monitoring de l'application.
 */
@Configuration
public class MetricsConfig {

    /**
     * Configure timed aspect for method execution monitoring.
     * Configure l'aspect temporisé pour le monitoring d'exécution des méthodes.
     * 
     * @param registry - Meter registry
     * @return TimedAspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
} 
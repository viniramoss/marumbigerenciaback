package br.com.marumbi.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Configuration
public class HealthConfig {

    @Bean
    public HealthIndicator customHealthIndicator() {
        return () -> Health.up()
                .withDetail("status", "UP")
                .withDetail("service", "Marumbi Backend")
                .build();
    }
    
    @RestController
    @RequestMapping("/actuator")
    public static class HealthController {
        
        @GetMapping("/health")
        public Map<String, Object> health() {
            return Collections.singletonMap("status", "UP");
        }
        
        @GetMapping("/info")
        public Map<String, Object> info() {
            return Collections.singletonMap("application", "Marumbi Backend Service");
        }
    }
}
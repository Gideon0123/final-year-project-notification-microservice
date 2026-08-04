package com.example.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutboxConfig {

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper()
                .findAndRegisterModules();

    }

}
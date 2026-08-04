package com.example.notification_service.service;

import com.example.notification_service.entity.OutboxEvent;
import com.example.notification_service.enums.OutboxEventType;
import com.example.notification_service.enums.OutboxStatus;
import com.example.notification_service.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OutboxServiceImpl implements OutboxService {

    private final OutboxEventRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void saveEvent(
            OutboxEventType type,
            Object event
    ) {

        try {
            String payload = objectMapper.writeValueAsString(
                    event
            );

            OutboxEvent outbox =
                    OutboxEvent.builder()
                            .eventType(type)
                            .payload(payload)
                            .status(OutboxStatus.PENDING)
                            .retryCount(0)
                            .createdAt(LocalDateTime.now())
                            .build();

            repository.save(outbox);

        } catch (JsonProcessingException ex) {

            throw new RuntimeException(
                    "Unable to serialize outbox event.",
                    ex
            );

        }

    }

}
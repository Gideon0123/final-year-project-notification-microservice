package com.example.notification_service.service;

import com.example.notification_service.enums.OutboxEventType;

public interface OutboxService {

    void saveEvent(
            OutboxEventType type,
            Object event
    );

}
package com.example.notification_service.service;

import com.example.notification_service.dto.event.PlagiarismCheckCompletedEvent;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.enums.NotificationType;

public interface NotificationService {

    void notify(
            Long recipientId,
            String recipientEmail,
            String title,
            String message,
            NotificationType type

    );

    void processPlagiarismNotification(
            PlagiarismCheckCompletedEvent event
    );

}
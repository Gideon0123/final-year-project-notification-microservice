package com.example.notification_service.service;

import com.example.notification_service.enums.NotificationType;

public interface NotificationService {

    void notify(
            Long recipientId,
            String recipientEmail,
            String title,
            String message,
            NotificationType type

    );

}
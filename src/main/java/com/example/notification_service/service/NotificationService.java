package com.example.notification_service.service;

import com.example.notification_service.enums.NotificationType;

public interface NotificationService {

    void createNotification(
            Long userId,
            String title,
            String message,
            NotificationType type
    );


}
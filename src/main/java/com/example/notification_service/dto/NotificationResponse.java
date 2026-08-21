package com.example.notification_service.dto;

import com.example.notification_service.enums.NotificationStatus;
import com.example.notification_service.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponse(

        Long id,
        String title,
        String message,
        NotificationType type,
        NotificationStatus status,
        Boolean read,
        LocalDateTime createdAt,
        LocalDateTime sentAt

) {
}
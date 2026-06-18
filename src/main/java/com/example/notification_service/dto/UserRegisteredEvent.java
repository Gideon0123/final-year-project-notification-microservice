package com.example.notification_service.dto;

import java.time.LocalDateTime;

public record UserRegisteredEvent(
        Long userId,
        String firstName,
        String email,
        LocalDateTime createdAt
) {
}

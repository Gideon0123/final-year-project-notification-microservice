package com.example.notification_service.dto.event;

import java.time.LocalDateTime;

public record UserRegisteredEvent(
        Long userId,
        String firstName,
        String email,
        String verificationToken,
        LocalDateTime createdAt
) {
}
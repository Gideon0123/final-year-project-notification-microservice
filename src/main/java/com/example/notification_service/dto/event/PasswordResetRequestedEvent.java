package com.example.notification_service.dto.event;

import java.time.LocalDateTime;

public record PasswordResetRequestedEvent(

        Long userId,
        String email,
        String firstName,
        String token,
        LocalDateTime requestedAt

) {}

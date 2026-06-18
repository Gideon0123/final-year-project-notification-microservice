package com.example.notification_service.dto.event;

import java.time.LocalDateTime;

public record VerificationEmailRequestedEvent(

        Long userId,
        String email,
        String firstName,
        String verificationToken,
        LocalDateTime requestedAt

) {}
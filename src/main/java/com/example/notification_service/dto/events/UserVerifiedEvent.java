package com.example.notification_service.dto.events;

import java.time.LocalDateTime;

public record UserVerifiedEvent(

        Long userId,
        String email,
        String firstName,
        LocalDateTime verifiedAt

) {}
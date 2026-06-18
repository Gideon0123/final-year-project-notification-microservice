package com.example.notification_service.dto.event;

import java.time.LocalDateTime;

public record UserVerifiedEvent(

        Long userId,
        String email,
        String firstName,
        LocalDateTime verifiedAt

) {}
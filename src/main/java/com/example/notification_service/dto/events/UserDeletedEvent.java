package com.example.notification_service.dto.events;

import java.time.LocalDateTime;

public record UserDeletedEvent(

        Long userId,
        String email,
        String firstName,
        LocalDateTime deletedAt

) {}

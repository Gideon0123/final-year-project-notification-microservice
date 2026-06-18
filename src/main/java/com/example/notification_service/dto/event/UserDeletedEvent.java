package com.example.notification_service.dto.event;

import java.time.LocalDateTime;

public record UserDeletedEvent(

        Long userId,
        String email,
        String firstName,
        LocalDateTime deletedAt

) {}

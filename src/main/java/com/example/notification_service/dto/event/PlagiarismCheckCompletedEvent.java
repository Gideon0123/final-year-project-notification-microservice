package com.example.notification_service.dto.event;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlagiarismCheckCompletedEvent(

        Long checkId,
        Long paperId,
        Long authorId,
        String authorEmail,
        Double similarityPercentage,
        String result,
        String summary,
        LocalDateTime completedAt

) {
}
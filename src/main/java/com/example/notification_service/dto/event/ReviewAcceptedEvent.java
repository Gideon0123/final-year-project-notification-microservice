package com.example.notification_service.dto.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAcceptedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

    private LocalDateTime acceptedAt;

}
package com.example.notification_service.dto.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAssignedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

    private Integer reviewRound;

    private LocalDateTime deadline;

    private Integer revisionNumber;

}
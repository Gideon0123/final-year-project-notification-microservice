package com.example.notification_service.dto.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRequestedEvent {

    private Long paperId;

    private Integer revisionNumber;

    private LocalDateTime submittedAt;

}
package com.example.notification_service.dto.event;

import com.example.notification_service.enums.EditorialDecision;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorialDecisionEvent {

    private Long paperId;

    private Long reviewId;

    private Long editorId;

    private EditorialDecision decision;

    private LocalDateTime decisionAt;

}
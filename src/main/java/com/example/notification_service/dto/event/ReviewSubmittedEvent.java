package com.example.notification_service.dto.event;

import com.example.notification_service.enums.ReviewRecommendation;
import com.example.notification_service.enums.ReviewScore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSubmittedEvent {

    private Long reviewId;

    private Long paperId;

    private Long reviewerId;

    private String reviewerEmail;

    private ReviewRecommendation recommendation;

    private ReviewScore overallScore;

    private LocalDateTime submittedAt;

    private Boolean requiresEditorialAttention;

    private String editorialAttentionReason;

}
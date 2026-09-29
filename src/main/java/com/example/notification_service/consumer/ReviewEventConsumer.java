package com.example.notification_service.consumer;

import com.example.notification_service.config.RabbitMQConfig;
import com.example.notification_service.dto.events.*;
import com.example.notification_service.enums.NotificationType;
import com.example.notification_service.service.NotificationService;
import com.example.notification_service.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.REVIEW_ASSIGNMENT_QUEUE)
    public void handleReviewAssigned(ReviewAssignedEvent event) {
        notificationService.processReviewNotification(

                event.getReviewerId(),
                event.getReviewerEmail(),
                "New Review Assignment",

                "You have been assigned a new paper to review with ID "
                        + event.getPaperId()
                        + "\n\n"
                        + "The deadline for you to accept this review is "
                        + event.getDeadline()
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Review Round: "
                        + event.getReviewRound()
                        + "\n"
                        + "Revision Number: "
                        + event.getRevisionNumber(),

                NotificationType.REVIEW_ASSIGNED
        );
    }

    @RabbitListener(queues = RabbitMQConfig.REVIEW_ACCEPTED_QUEUE)
    public void handleAccepted(ReviewAcceptedEvent event) {

        notificationService.processReviewNotification(

                event.getReviewerId(),
                event.getReviewerEmail(),

                "Review Accepted",

                "You accepted the review invitation."
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Review ID: "
                        + event.getReviewId()
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Accepted At: "
                        + event.getAcceptedAt(),

                NotificationType.REVIEW_ACCEPTED
        );
    }

    @RabbitListener(queues = RabbitMQConfig.REVIEW_DECLINED_QUEUE)
    public void handleDeclined(ReviewDeclinedEvent event) {

        notificationService.processReviewNotification(

                event.getReviewerId(),
                event.getReviewerEmail(),

                "Review Declined",

                "You declined the review invitation."
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Review ID: "
                        + event.getReviewId()
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Reason: "
                        + event.getReason()
                        + "\n"
                        + "Declined At: "
                        + event.getDeclinedAt(),

                NotificationType.REVIEW_DECLINED
        );
    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_DECISION_QUEUE
    )
    public void handleDecision(
            EditorialDecisionEvent event
    ) {

        String message;
        NotificationType type;

        switch (event.getDecision()) {

            case ACCEPT -> {
                message = "Your paper has been accepted.";
                type = NotificationType.PAPER_ACCEPTED;
            }

            case MINOR_REVISION, MAJOR_REVISION -> {
                message = "Your paper requires revision.";
                type = NotificationType.PAPER_REVISION_REQUIRED;
            }

            case REJECT -> {
                message = "Your paper has been rejected.";
                type = NotificationType.PAPER_REJECTED;
            }

            default -> {
                message = "The editorial decision for your paper has been updated.";
                type = NotificationType.PAPER_ACCEPTED;
            }
        }

        notificationService.processReviewNotification(

                event.getAuthorId(),
                event.getRecipientEmail(),
                "Editorial Decision",
                message,
                type
        );
    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_SUBMITTED_QUEUE
    )
    public void handleSubmitted(
            ReviewSubmittedEvent event
    ) {

        notificationService.processReviewNotification(

                event.getReviewerId(),
                event.getReviewerEmail(),
                "Review Submitted",
                "Your review has been successfully submitted."
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Review ID: "
                        + event.getReviewId()
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Overall Score: "
                        + event.getOverallScore()
                        + "\n"
                        + "Recommendation: "
                        + event.getRecommendation()
                        + "\n"
                        + "Editorial Attention Reason: "
                        + event.getEditorialAttentionReason()
                        + "\n"
                        + "Requires Editorial Attention? "
                        + event.getRequiresEditorialAttention()
                        + "\n"
                        + "Submitted At: "
                        + event.getSubmittedAt(),

                NotificationType.REVIEW_SUBMITTED
        );
    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_REVISION_QUEUE
    )
    public void handleRevision(
            RevisionRequestedEvent event
    ) {

        notificationService.processReviewNotification(

                event.getAuthorId(),
                event.getAuthorEmail(),
                "Revision Requested",
                "A revision has been requested for your paper."
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Revision Number: "
                        + event.getRevisionNumber()
                        + "\n"
                        + "Requested At: "
                        + event.getSubmittedAt(),

                NotificationType.PAPER_REVISION_REQUIRED
        );
    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_REMINDER_QUEUE
    )
    public void handleReminder(
            ReviewReminderEvent event
    ) {

        notificationService.processReviewNotification(

                event.getReviewerId(),
                event.getReviewerEmail(),
                "Review Reminder",
                "This is a reminder that your assigned review is approaching its deadline."
                        + "\n\n"
                        + "Details of the review:"
                        + "\n"
                        + "Review ID: "
                        + event.getReviewId()
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Deadline: "
                        + event.getDeadline(),

                NotificationType.REVIEW_REMINDER
        );
    }

    @RabbitListener(
            queues = RabbitMQConstants.REVIEW_ESCALATION_QUEUE
    )
    public void handleEscalation(
            ReviewEscalationEvent event
    ) {

        notificationService.processReviewNotification(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "Review Deadline Escalation",

                "Your assigned review has passed its deadline."
                        + "\n\n"
                        + "Review ID: "
                        + event.getReviewId()
                        + "\n"
                        + "Paper ID: "
                        + event.getPaperId()
                        + "\n"
                        + "Deadline: "
                        + event.getDeadline()
                        + "\n"
                        + "Escalated At: "
                        + event.getEscalatedAt(),

                NotificationType.REVIEW_ESCALATION
        );
    }

}
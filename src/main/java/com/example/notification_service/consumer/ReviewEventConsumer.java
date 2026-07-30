package com.example.notification_service.consumer;

import com.example.notification_service.config.RabbitMQConfig;
import com.example.notification_service.dto.event.*;
import com.example.notification_service.enums.NotificationType;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_ASSIGNMENT_QUEUE
    )
    public void handleReviewAssigned(
            ReviewAssignedEvent event
    ){
        notificationService.notify(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "New Review Assignment",

                "You have been assigned a new paper to review paper with ID " + event.getPaperId() + " " +
                        "The deadline for you to accept this review is " + event.getDeadline() + "\n " +
                        "Details of the review are as follows " + "\n " +
                        "paper ID: " + event.getPaperId() + "\n " +
                        "ReviewRound: " + event.getReviewRound() + "\n " +
                        "RevisionNumber: " + event.getRevisionNumber()  ,

                NotificationType.REVIEW_ASSIGNED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_ACCEPTED_QUEUE
    )
    public void handleAccepted(
            ReviewAcceptedEvent event
    ){
        notificationService.notify(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "Review Accepted",

                "You accepted the review invitation." + "\n" +
                        "Details of the Review are as follows:" + "\n" +
                        "Review ID: " + event.getReviewId() + "\n" +
                        "Paper ID: " + event.getPaperId() + "\n" +
                        "The Review was accepted at: " + event.getAcceptedAt() ,

                NotificationType.REVIEW_ACCEPTED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_DECLINED_QUEUE
    )
    public void handleDeclined(
            ReviewDeclinedEvent event
    ){
        notificationService.notify(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "Review Declined",

                "You Declined the review invitation." + "\n" +
                        "Details of the Review are as follows:" + "\n" +
                        "Review ID: " + event.getReviewId() + "\n" +
                        "Paper ID: " + event.getPaperId() + "\n" +
                        "Reason: " + event.getReason() + "\n" +
                        "The Review was accepted at: " + event.getDeclinedAt() ,

                NotificationType.REVIEW_DECLINED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_DECISION_QUEUE
    )
    public void handleDecision(
            EditorialDecisionEvent event
    ){
        String message;

        switch(event.getDecision()){

            case ACCEPT -> message = "Your paper has been accepted.";

            case MINOR_REVISION, MAJOR_REVISION -> message = "Your paper requires revision.";

            case REJECT -> message = "Your paper has been rejected.";

            default -> message = "Decision updated.";

        }

        notificationService.notify(

                event.getAuthorId(),

                event.getRecipientEmail(),

                "Editorial Decision",

                message,

                NotificationType.PAPER_ACCEPTED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_SUBMITTED_QUEUE
    )
    public void handleSubmitted(
            ReviewSubmittedEvent event
    ){
        notificationService.notify(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "Review Submitted",

                "\"Your review has been successfully submitted." + "\n" +
                        "Details of the Review are as follows:" + "\n" +
                        "Review ID: " + event.getReviewId() + "\n" +
                        "Paper ID: " + event.getPaperId() + "\n" +
                        "OverallScore: " + event.getOverallScore().toString() + "\n" +
                        "Recommendation: " + event.getRecommendation().toString() + "\n" +
                        "Editorial Attention Reason: " + event.getEditorialAttentionReason() + "\n" +
                        "Requires Editorial Attention? " + event.getRequiresEditorialAttention() + "\n" +
                        "The Review was accepted at: " + event.getSubmittedAt() ,

                NotificationType.REVIEW_SUBMITTED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_REVISION_QUEUE
    )
    public void handleRevision(
            RevisionRequestedEvent event
    ){
        notificationService.notify(

                event.getAuthorId(),

                event.getAuthorEmail(),

                "Revision Requested",

                "Review Revision was Requested." + "\n" +
                        "Details of the Review are as follows:" + "\n" +
                        "Paper ID: " + event.getPaperId() + "\n" +
                        "Revision Number: " + event.getRevisionNumber() + "\n" +
                        "The Review was accepted at: " + event.getSubmittedAt() ,

                NotificationType.PAPER_REVISION_REQUIRED
        );

    }

    @RabbitListener(
            queues = RabbitMQConfig.REVIEW_REMINDER_QUEUE
    )
    public void handleReminder(
            ReviewReminderEvent event
    ){
        notificationService.notify(

                event.getReviewerId(),

                event.getReviewerEmail(),

                "Revision Reminder",

                "This is a reminder that your assigned review is approaching its deadline." + "\n" +
                        "Details of the Review are as follows:" + "\n" +
                        "Review ID: " + event.getReviewId() + "\n" +
                        "Paper ID: " + event.getPaperId() + "\n" +
                        "Deadline: " + event.getDeadline(),

                NotificationType.REVIEW_REMINDER
        );

    }

}
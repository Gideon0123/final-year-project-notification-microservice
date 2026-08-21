package com.example.notification_service.service;

import com.example.notification_service.dto.event.PlagiarismCheckCompletedEvent;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.enums.NotificationStatus;
import com.example.notification_service.enums.NotificationType;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Override
    public void notify(
            Long recipientId,
            String recipientEmail,
            String title,
            String message,
            NotificationType type
    ) {
        Notification notification =
                Notification.builder()
                        .recipientId(recipientId)
                        .recipientEmail(recipientEmail)
                        .title(title)
                        .message(message)
                        .type(type)
                        .status(NotificationStatus.PENDING)
                        .read(false)
                        .retryCount(0)
                        .build();

        notificationRepository.save(notification);

        try{
            emailService.send(notification);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notification.setFailureReason(null);

        }

        catch(Exception ex){
            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
        }

        notificationRepository.save(notification);

    }

    @Override
    public void processPlagiarismNotification(PlagiarismCheckCompletedEvent event) {

        Notification notification =
                Notification.builder()
                        .recipientId(event.authorId())
                        .recipientEmail(event.authorEmail())
                        .title("Plagiarism Check Completed")
                        .message(buildMessage(event))
                        .type(NotificationType.PLAGIARISM)
                        .status(NotificationStatus.PENDING)
                        .build();
        notification = notificationRepository.save(notification);

        try {
//            emailService.send(notification);
            emailService.sendPlagiarismResultEmail(notification);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        } catch (Exception ex) {

            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
        }
    }

    private String buildMessage(
            PlagiarismCheckCompletedEvent event
    ) {
        return """
            Your plagiarism analysis has completed.

            Paper ID: %d

            Similarity: %.2f%%

            Result: %s

            Summary:
            %s
            """
                .formatted(
                        event.paperId(),
                        event.similarityPercentage(),
                        event.result(),
                        event.summary()
                );
    }
}
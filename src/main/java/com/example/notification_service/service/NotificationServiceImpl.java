package com.example.notification_service.service;

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

}
package com.example.notification_service.service;

import com.example.notification_service.entity.Notification;
import com.example.notification_service.enums.NotificationType;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public void createNotification(
            Long userId,
            String title,
            String message,
            NotificationType type
    ){
        Notification notification =
                Notification.builder()
                        .userId(userId)
                        .title(title)
                        .message(message)
                        .type(type)
                        .read(false)
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();



        repository.save(notification);

    }

}
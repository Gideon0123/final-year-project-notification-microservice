package com.example.notification_service.service;

import com.example.notification_service.mapper.NotificationMapper;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.exception.AccessDeniedException;
import com.example.notification_service.exception.ResourceNotFoundException;
import com.example.notification_service.payload.PagedResponse;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    private Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }


    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NotificationResponse> getMyNotifications(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        Page<NotificationResponse> notifications =
                repository.findByRecipientIdOrderByCreatedAtDesc(
                        userId,
                        pageable
                ).map(mapper::toResponse);

        return new PagedResponse<>(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NotificationResponse> getUnreadNotifications(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        Page<NotificationResponse> notifications =
                repository.findByRecipientIdAndReadFalseOrderByCreatedAtDesc(
                        userId,
                        pageable
                ).map(mapper::toResponse);

        return new PagedResponse<>(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(
            Long userId,
            Long notificationId
    ) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Notification not found"
                        )
                );

        if (!notification.getRecipientId().equals(userId)) {
            throw new AccessDeniedException(
                    "You do not own this notification"
            );
        }

        return mapper.toResponse(notification);
    }

    @Override
    public void markAsRead(
            Long userId,
            Long notificationId
    ) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Notification not found"
                        )
                );

        if (!notification.getRecipientId().equals(userId)) {
            throw new AccessDeniedException(
                    "You do not own this notification"
            );
        }

        notification.setRead(true);
    }

    @Override
    public void markAllAsRead(
            Long userId
    ) {
        repository.findByRecipientIdAndReadFalseOrderByCreatedAtDesc(
                        userId,
                        Pageable.unpaged()
                )
                .forEach(notification -> notification.setRead(true));
    }

    @Override
    @Transactional(readOnly = true)
    public long unreadCount(
            Long userId
    ) {
        return repository.countByRecipientIdAndReadFalse(
                userId
        );
    }
}

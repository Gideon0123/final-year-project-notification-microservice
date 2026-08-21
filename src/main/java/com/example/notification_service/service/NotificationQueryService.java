package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.payload.PagedResponse;

public interface NotificationQueryService {

    PagedResponse<NotificationResponse> getMyNotifications(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    PagedResponse<NotificationResponse> getUnreadNotifications(
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    NotificationResponse getNotification(
            Long userId,
            Long notificationId
    );
    void markAsRead(
            Long userId,
            Long notificationId
    );
    void markAllAsRead(
            Long userId
    );
    long unreadCount(
            Long userId
    );

    void deleteNotification(
            Long userId,
            Long notificationId
    );
}

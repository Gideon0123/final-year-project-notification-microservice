package com.example.notification_service.mapper;

import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(
            Notification notification
    );
}
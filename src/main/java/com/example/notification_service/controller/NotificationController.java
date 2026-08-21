package com.example.notification_service.controller;

import com.example.notification_service.dto.ApiResponse;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.payload.PagedResponse;
import com.example.notification_service.service.NotificationQueryService;
import com.example.notification_service.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationQueryService notificationService;

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getMyNotifications(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<NotificationResponse> notifications =
                notificationService.getMyNotifications(
                        userId, adjustedPage, size, sortBy, sortDirection
                );

        PagedResponse<NotificationResponse> response =
                PagedResponse.<NotificationResponse>builder()
                        .content(notifications.getContent())
                        .size(notifications.getSize())
                        .page(notifications.getPage())
                        .first(notifications.isFirst())
                        .last(notifications.isLast())
                        .totalElements(notifications.getTotalElements())
                        .totalPages(notifications.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<NotificationResponse>>builder()
                        .success(true)
                        .message("Notifications fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @GetMapping("/unread")
    public ResponseEntity<ApiResponse<PagedResponse<NotificationResponse>>> getUnreadNotifications(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<NotificationResponse> notifications =
                notificationService.getUnreadNotifications(
                        userId, adjustedPage, size, sortBy, sortDirection
                );

        PagedResponse<NotificationResponse> response =
                PagedResponse.<NotificationResponse>builder()
                        .content(notifications.getContent())
                        .size(notifications.getSize())
                        .page(notifications.getPage())
                        .first(notifications.isFirst())
                        .last(notifications.isLast())
                        .totalElements(notifications.getTotalElements())
                        .totalPages(notifications.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<NotificationResponse>>builder()
                        .success(true)
                        .message("Notifications fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long notificationId,
            HttpServletRequest request
    ) {
        NotificationResponse response = notificationService.getNotification(
                userId,
                notificationId
        );

        return ResponseEntity.ok(
                ApiResponse.<NotificationResponse>builder()
                        .success(true)
                        .message("Notification fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long notificationId,
            HttpServletRequest request
    ) {
        notificationService.markAsRead(userId, notificationId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Notification marked as read")
                        .status(HttpStatus.OK.value())
                        .data(null)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @RequestHeader("X-USER-ID") Long userId,
            HttpServletRequest request
    ) {
        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("All notifications marked as read")
                        .status(HttpStatus.OK.value())
                        .data(null)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Long>> unreadCount(
            @RequestHeader("X-USER-ID") Long userId,
            HttpServletRequest request
    ) {
        long count = notificationService.unreadCount(userId);

        return ResponseEntity.ok(
                ApiResponse.<Long>builder()
                        .success(true)
                        .message("Unread count fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(count)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESEARCHER','REVIEWER','STUDENT', 'LECTURER')")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long notificationId,
            HttpServletRequest request
    ) {
        notificationService.deleteNotification(
                userId,
                notificationId
        );

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Notification deleted successfully")
                        .status(HttpStatus.OK.value())
                        .data(null)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}

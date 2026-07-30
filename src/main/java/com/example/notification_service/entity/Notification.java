package com.example.notification_service.entity;

import com.example.notification_service.enums.NotificationStatus;
import com.example.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipientId;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private String title;

    @Lob
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Builder.Default
    @Column(name = "is_read")
    private Boolean read = false;

    @Builder.Default
    private Integer retryCount = 0;

    private String failureReason;

    private LocalDateTime sentAt;

    @CreatedDate
    private LocalDateTime createdAt;
}
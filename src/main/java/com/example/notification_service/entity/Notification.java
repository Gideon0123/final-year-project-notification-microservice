package com.example.notification_service.entity;

import com.example.notification_service.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId;


    private String title;


    @Column(length = 1000)
    private String message;


    @Enumerated(EnumType.STRING)
    private NotificationType type;


    private boolean read;


    private LocalDateTime createdAt;


}
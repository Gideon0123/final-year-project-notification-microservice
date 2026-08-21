package com.example.notification_service.repository;

import com.example.notification_service.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    long countByRecipientIdAndReadFalse(
            Long recipientId
    );

    Page<Notification> findByRecipientIdOrderByCreatedAtDesc(
            Long recipientId,
            Pageable pageable
    );

    Page<Notification> findByRecipientIdAndReadFalseOrderByCreatedAtDesc(
            Long recipientId,
            Pageable pageable
    );

}
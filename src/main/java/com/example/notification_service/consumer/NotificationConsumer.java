package com.example.notification_service.consumer;

import com.example.notification_service.dto.event.*;
import com.example.notification_service.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "user.registered.queue")
    public void consumeUserRegistered(
            UserRegisteredEvent event
    ) {
        log.info("Received UserRegisteredEvent {}", event.email());
        emailService.sendWelcomeEmail(event);
    }

    @RabbitListener(queues = "verification.requested.queue")
    public void consumeVerificationRequested(
            VerificationEmailRequestedEvent event
    ) throws MessagingException {
        log.info("Received VerificationEmailRequestedEvent {}", event.email());
        emailService.sendVerificationEmail(event);
    }

    @RabbitListener(queues = "user.verified.queue")
    public void consumeUserVerified(
            UserVerifiedEvent event
    ) {
        log.info("Received UserVerifiedEvent {}", event.email());
        emailService.sendVerifiedEmail(event);
    }

    @RabbitListener(queues = "user.deleted.queue")
    public void consumeUserDeleted(
            UserDeletedEvent event
    ) throws MessagingException {
        log.info("Received UserDeletedEvent {}", event.email());
        emailService.sendGoodbyeEmail(event);
    }

    @RabbitListener(queues = "password.reset.queue")
    public void consumePasswordReset(
            PasswordResetRequestedEvent event
    ) {
        log.info("Received PasswordResetRequestedEvent {}", event.email());
        emailService.sendPasswordResetEmail(event);
    }
}
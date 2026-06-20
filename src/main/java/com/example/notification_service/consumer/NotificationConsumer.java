package com.example.notification_service.consumer;

import com.example.notification_service.dto.event.*;
import com.example.notification_service.service.EmailService;
import com.example.notification_service.utils.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    @PostConstruct
    public void init() {
        log.info("NotificationConsumer Loaded");
    }

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConstants.USER_REGISTERED_QUEUE)
    public void consumeUserRegistered(
            UserRegisteredEvent event
    ) {
        log.info("Received UserRegisteredEvent {}", event.email());
        emailService.sendWelcomeEmail(event);
    }

    @RabbitListener(queues = RabbitMQConstants.VERIFICATION_REQUESTED_QUEUE)
    public void consumeVerificationRequested(
            VerificationEmailRequestedEvent event
    ) throws MessagingException {
        log.info("Received VerificationEmailRequestedEvent {}", event.email());
        emailService.sendVerificationEmail(event);
    }

    @RabbitListener(queues = RabbitMQConstants.USER_VERIFIED_QUEUE)
    public void consumeUserVerified(
            UserVerifiedEvent event
    ) {
        log.info("Received UserVerifiedEvent {}", event.email());
        emailService.sendVerifiedEmail(event);
    }

    @RabbitListener(queues = RabbitMQConstants.USER_DELETED_QUEUE)
    public void consumeUserDeleted(
            UserDeletedEvent event
    ) throws MessagingException {
        log.info("Received UserDeletedEvent {}", event.email());
        emailService.sendGoodbyeEmail(event);
    }

    @RabbitListener(queues = RabbitMQConstants.PASSWORD_RESET_QUEUE)
    public void consumePasswordReset(
            PasswordResetRequestedEvent event
    ) {
        log.info("Received PasswordResetRequestedEvent {}", event.email());
        emailService.sendPasswordResetEmail(event);
    }
}
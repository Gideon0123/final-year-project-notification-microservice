package com.example.notification_service.service;

import com.example.notification_service.dto.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendWelcomeEmail(UserRegisteredEvent event) {

    }
    @Recover
    public void recoverWelcomeEmail(
            Exception ex,
            UserRegisteredEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendVerificationEmail(VerificationEmailRequestedEvent event) {

    }
    @Recover
    public void recoverVerificationEmail(
            Exception ex,
            VerificationEmailRequestedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendVerifiedEmail(UserVerifiedEvent event) {

    }
    @Recover
    public void recoverVerifiedEmail(
            Exception ex,
            UserVerifiedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendPasswordResetEmail(PasswordResetRequestedEvent event) {

    }
    @Recover
    public void recoverPasswordResetEmail(
            Exception ex,
            PasswordResetRequestedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendGoodbyeEmail(UserDeletedEvent event) {

    }
    @Recover
    public void recoverGoodbyeEmail(
            Exception ex,
            UserDeletedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }
}
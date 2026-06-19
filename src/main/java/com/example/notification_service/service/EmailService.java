package com.example.notification_service.service;

import com.example.notification_service.dto.event.*;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendWelcomeEmail(UserRegisteredEvent event);
    void sendVerificationEmail(VerificationEmailRequestedEvent event) throws MessagingException;
    void sendVerifiedEmail(UserVerifiedEvent event);
    void sendPasswordResetEmail(PasswordResetRequestedEvent event);
    void sendGoodbyeEmail(UserDeletedEvent event) throws MessagingException;
}
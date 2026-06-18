package com.example.notification_service.service;

import com.example.notification_service.dto.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(UserRegisteredEvent event) {

    }

    @Override
    public void sendVerificationEmail(VerificationEmailRequestedEvent event) {

    }

    @Override
    public void sendVerifiedEmail(UserVerifiedEvent event) {

    }

    @Override
    public void sendPasswordResetEmail(PasswordResetRequestedEvent event) {

    }

    @Override
    public void sendGoodbyeEmail(UserDeletedEvent event) {

    }
}
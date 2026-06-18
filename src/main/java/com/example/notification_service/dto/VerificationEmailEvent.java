package com.example.notification_service.dto;

public record VerificationEmailEvent(
        Long userId,
        String email,
        String firstName,
        String verificationToken
) {
}

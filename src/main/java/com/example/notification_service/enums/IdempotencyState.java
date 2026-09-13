package com.example.notification_service.enums;

public enum IdempotencyState {
    NOT_FOUND,
    PROCESSING,
    COMPLETED,
    FINGERPRINT_MISMATCH
}
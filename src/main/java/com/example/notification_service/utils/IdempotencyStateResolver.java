package com.example.notification_service.utils;


import com.example.notification_service.entity.IdempotencyRecord;
import com.example.notification_service.enums.IdempotencyState;
import com.example.notification_service.enums.IdempotencyStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IdempotencyStateResolver {

    public IdempotencyState resolve(
            Optional<IdempotencyRecord> record,
            String fingerprint
    ) {
        if (record.isEmpty()) {
            return IdempotencyState.NOT_FOUND;
        }

        IdempotencyRecord existing = record.get();

        if (!existing.getFingerprint().equals(fingerprint)) {
            return IdempotencyState.FINGERPRINT_MISMATCH;
        }

        if (existing.getStatus() == IdempotencyStatus.PROCESSING) {
            return IdempotencyState.PROCESSING;
        }

        return IdempotencyState.COMPLETED;
    }

}
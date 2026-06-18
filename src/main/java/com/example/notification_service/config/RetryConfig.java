package com.example.notification_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

//@EnableRetry
//@Configuration
//public class RetryConfig {
//
//    @Retryable(
//            retryFor = Exception.class,
//            maxAttempts = 5,
//            backoff = @Backoff(
//                    delay = 3000,
//                    multiplier = 2
//            )
//    )
//    public void send(...) {
//    }
//
//    @Recover
//    public void recover(
//            Exception ex,
//            String to,
//            String subject,
//            String body) {
//
//        log.error(
//                "Email permanently failed {}",
//                to
//        );
//    }
//}
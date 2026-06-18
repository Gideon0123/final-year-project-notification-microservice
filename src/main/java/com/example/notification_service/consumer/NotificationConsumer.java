package com.example.notification_service.consumer;

import com.example.notification_service.dto.UserRegisteredEvent;
import com.example.notification_service.dto.VerificationEmailEvent;
import com.example.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
//@RequiredArgsConstructor
//public class NotificationConsumer {
//
//    private final EmailService emailService;
//
//    @RabbitListener(queues = "notification.email.queue")
//    public void handle(
//            UserRegisteredEvent event
//    ) {
//
//        emailService.send(
//                event.email(),
//                "Welcome to ResearchHub",
//                buildWelcomeTemplate(event)
//        );
//    }
//
//    @RabbitListener(
//            queues = "notification.email.queue"
//    )
//    public void handle(
//            VerificationEmailEvent event) {
//
//        emailService.send(
//                event.email(),
//                "Verify Your Email",
//                buildVerificationTemplate(event)
//        );
//    }
//}

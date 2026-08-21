package com.example.notification_service.service;

import com.example.notification_service.dto.event.PlagiarismCheckCompletedEvent;
import com.example.notification_service.utils.RabbitMQConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlagiarismNotificationListener {

    @RabbitListener(queues = RabbitMQConstants.PLAGIARISM_QUEUE)
    public void consume(
            PlagiarismCheckCompletedEvent event
    ) {
        log.info(
                "Received plagiarism event {}",
                event.checkId()
        );

        // email

        // in-app notification
    }
}

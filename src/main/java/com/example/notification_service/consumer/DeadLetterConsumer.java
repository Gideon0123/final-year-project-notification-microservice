package com.example.notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadLetterConsumer {

    @RabbitListener(queues = "password.reset.dlq")
    public void consumePasswordResetDLQ(
            Message message
    ) {
        log.error(
                "Password Reset Email Failed {}",
                new String(message.getBody())
        );
    }

    @RabbitListener(queues = "user.deleted.dlq")
    public void consumeUserDeletedDLQ(
            Message message
    ) {
        log.error(
                "User Delete Email Failed {}",
                new String(message.getBody())
        );
    }

    @RabbitListener(queues = "verification.requested.dlq")
    public void consumeVerificationRequestedDLQ(
            Message message
    ) {
        log.error(
                "Verification Requested Email Failed {}",
                new String(message.getBody())
        );
    }

    @RabbitListener(queues = "user.verified.dlq")
    public void consumeUserVerifiedDLQ(
            Message message
    ) {
        log.error(
                "User Verification Email Failed {}",
                new String(message.getBody())
        );
    }

    @RabbitListener(queues = "user.registered.dlq")
    public void consumeUserRegisteredDLQ(
            Message message
    ) {
        log.error(
                "User Registration Email Failed {}",
                new String(message.getBody())
        );
    }
}

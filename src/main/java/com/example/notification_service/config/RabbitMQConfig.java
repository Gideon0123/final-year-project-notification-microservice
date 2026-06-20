package com.example.notification_service.config;

import com.example.notification_service.utils.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    public static final String EXCHANGE = "notification.exchange";
//    public static final String EXCHANGE = "researchhub.exchange";

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    public Queue userRegisteredQueue() {
        return QueueBuilder.durable(RabbitMQConstants.USER_REGISTERED_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.EXCHANGE
                )
                .withArgument(
                        "x-dead-letter-routing-key",
                        "user.registered.dlq"
                )
                .build();
    }

    @Bean
    public Queue userRegisteredDLQ() {
        return QueueBuilder.durable(
                "user.registered.dlq"
        ).build();
    }

    @Bean
    public Binding userRegisteredBinding(
            Queue userRegisteredQueue,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userRegisteredQueue)
                .to(notificationExchange)
                .with(RabbitMQConstants.USER_REGISTERED_ROUTING_KEY);
    }

    @Bean
    public Binding userRegisteredDLQBinding(
            Queue userRegisteredDLQ,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userRegisteredDLQ)
                .to(notificationExchange)
                .with("user.registered.dlq");
    }

    @Bean
    public Queue userVerifiedQueue() {
        return QueueBuilder.durable(RabbitMQConstants.USER_VERIFIED_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.EXCHANGE
                )
                .withArgument(
                        "x-dead-letter-routing-key",
                        "user.verified.dlq"
                )
                .build();
    }

    @Bean
    public Queue userVerifiedDLQ() {
        return QueueBuilder.durable(
                "user.verified.dlq"
        ).build();
    }

    @Bean
    public Binding userVerifiedBinding(
            Queue userVerifiedQueue,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userVerifiedQueue)
                .to(notificationExchange)
                .with(RabbitMQConstants.USER_VERIFIED_ROUTING_KEY);
    }

    @Bean
    public Binding userVerifiedDLQBinding(
            Queue userVerifiedDLQ,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userVerifiedDLQ)
                .to(notificationExchange)
                .with("user.verified.dlq");
    }

    @Bean
    public Queue verificationRequestedQueue() {
        return QueueBuilder.durable(RabbitMQConstants.VERIFICATION_REQUESTED_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.EXCHANGE
                )
                .withArgument(
                        "x-dead-letter-routing-key",
                        "verification.requested.dlq"
                )
                .build();
    }

    @Bean
    public Queue verificationRequestedDLQ() {
        return QueueBuilder.durable(
                "verification.requested.dlq"
        ).build();
    }

    @Bean
    public Binding verificationRequestedBinding(
            Queue verificationRequestedQueue,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(verificationRequestedQueue)
                .to(notificationExchange)
                .with(RabbitMQConstants.VERIFICATION_REQUESTED_ROUTING_KEY);
    }

    @Bean
    public Binding verificationRequestedDLQBinding(
            Queue verificationRequestedDLQ,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(verificationRequestedDLQ)
                .to(notificationExchange)
                .with("verification.requested.dlq");
    }

    @Bean
    public Queue userDeletedQueue() {
        return QueueBuilder.durable(RabbitMQConstants.USER_DELETED_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.EXCHANGE
                )
                .withArgument(
                        "x-dead-letter-routing-key",
                        "user.deleted.dlq"
                )
                .build();
    }

    @Bean
    public Queue userDeletedDLQ() {
        return QueueBuilder.durable(
                "user.deleted.dlq"
        ).build();
    }

    @Bean
    public Binding userDeletedBinding(
            Queue userDeletedQueue,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userDeletedQueue)
                .to(notificationExchange)
                .with(RabbitMQConstants.USER_DELETED_ROUTING_KEY);
    }

    @Bean
    public Binding userDeletedDLQBinding(
            Queue userDeletedDLQ,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(userDeletedDLQ)
                .to(notificationExchange)
                .with("user.deleted.dlq");
    }

    @Bean
    public Queue passwordResetQueue() {
        return QueueBuilder.durable(RabbitMQConstants.PASSWORD_RESET_QUEUE)
                .withArgument(
                        "x-dead-letter-exchange",
                        RabbitMQConstants.EXCHANGE
                )
                .withArgument(
                        "x-dead-letter-routing-key",
                        "password.reset.dlq"
                )
                .build();
    }

    @Bean
    public Queue passwordResetDLQ() {
        return QueueBuilder.durable(
                "password.reset.dlq"
        ).build();
    }

    @Bean
    public Binding passwordResetBinding(
            Queue passwordResetQueue,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(passwordResetQueue)
                .to(notificationExchange)
                .with(RabbitMQConstants.PASSWORD_RESET_ROUTING_KEY);
    }

    @Bean
    public Binding passwordResetDLQBinding(
            Queue passwordResetDLQ,
            TopicExchange notificationExchange
    ) {
        return BindingBuilder
                .bind(passwordResetDLQ)
                .to(notificationExchange)
                .with("password.reset.dlq");
    }

//    @Bean
//    public RetryOperationsInterceptor retryInterceptor() {
//
//        return RetryInterceptorBuilder
//                .stateless()
//                .backOffOptions(
//                        1000,
//                        2.0,
//                        10000
//                )
//                .recoverer(
//                        new RejectAndDontRequeueRecoverer()
//                )
//                .build();
//    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        factory.setMessageConverter(messageConverter);

        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}
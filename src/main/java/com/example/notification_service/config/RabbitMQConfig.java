package com.example.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "researchhub.exchange";

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue userRegisteredQueue() {
        return QueueBuilder.durable("user.registered.queue")
                .withArgument(
                        "x-dead-letter-exchange",
                        EXCHANGE
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
                .with("user.registered");
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
        return QueueBuilder.durable("user.verified.queue")
                .withArgument(
                        "x-dead-letter-exchange",
                        EXCHANGE
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
                .with("user.verified");
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
        return QueueBuilder.durable("verification.requested.queue")
                .withArgument(
                        "x-dead-letter-exchange",
                        EXCHANGE
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
                .with("verification.requested");
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
        return QueueBuilder.durable("user.deleted.queue")
                .withArgument(
                        "x-dead-letter-exchange",
                        EXCHANGE
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
                .with("user.deleted");
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
        return QueueBuilder.durable("password.reset.queue")
                .withArgument(
                        "x-dead-letter-exchange",
                        EXCHANGE
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
                .with("password.reset");
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
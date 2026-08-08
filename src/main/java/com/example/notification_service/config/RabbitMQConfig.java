package com.example.notification_service.config;

import com.example.notification_service.utils.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMQConfig {

    /*
     * ============================================================
     * REVIEW CONSTANTS
     * ============================================================
     */

    public static final String REVIEW_EXCHANGE =
            "review.exchange";

    public static final String REVIEW_ASSIGNMENT_QUEUE =
            "review.assignment.queue";

    public static final String REVIEW_ACCEPTED_QUEUE =
            "review.accepted.queue";

    public static final String REVIEW_DECLINED_QUEUE =
            "review.declined.queue";

    public static final String REVIEW_SUBMITTED_QUEUE =
            "review.submitted.queue";

    public static final String REVIEW_DECISION_QUEUE =
            "review.decision.queue";

    public static final String REVIEW_REMINDER_QUEUE =
            "review.reminder.queue";

    public static final String REVIEW_REVISION_QUEUE =
            "review.revision.queue";

    public static final String REVIEW_ACCEPTED_PAPER_QUEUE =
            "review.accepted.paper.queue";

    public static final String REVIEW_ESCALATION_QUEUE =
            "review.escalation.queue";

    public static final String REVIEW_DLX =
            "review.deadletter.exchange";

    public static final String REVIEW_DLQ =
            "review.deadletter.queue";

    public static final String REVIEW_DL_ROUTING_KEY =
            "review.dead";


    /*
     * ============================================================
     * NOTIFICATION EXCHANGE
     * ============================================================
     */

    @Bean
    public TopicExchange notificationExchange() {

        return ExchangeBuilder
                .topicExchange(
                        RabbitMQConstants.EXCHANGE
                )
                .durable(true)
                .build();
    }


    /*
     * ============================================================
     * USER REGISTERED
     * ============================================================
     */

    @Bean
    public Queue userRegisteredQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.USER_REGISTERED_QUEUE
                )
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

        return QueueBuilder
                .durable("user.registered.dlq")
                .build();
    }


    @Bean
    public Binding userRegisteredBinding(
            Queue userRegisteredQueue,
            TopicExchange notificationExchange
    ) {

        return BindingBuilder
                .bind(userRegisteredQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.USER_REGISTERED_ROUTING_KEY
                );
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


    /*
     * ============================================================
     * USER VERIFIED
     * ============================================================
     */

    @Bean
    public Queue userVerifiedQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.USER_VERIFIED_QUEUE
                )
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

        return QueueBuilder
                .durable("user.verified.dlq")
                .build();
    }


    @Bean
    public Binding userVerifiedBinding(
            Queue userVerifiedQueue,
            TopicExchange notificationExchange
    ) {

        return BindingBuilder
                .bind(userVerifiedQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.USER_VERIFIED_ROUTING_KEY
                );
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


    /*
     * ============================================================
     * VERIFICATION REQUESTED
     * ============================================================
     */

    @Bean
    public Queue verificationRequestedQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.VERIFICATION_REQUESTED_QUEUE
                )
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

        return QueueBuilder
                .durable("verification.requested.dlq")
                .build();
    }


    @Bean
    public Binding verificationRequestedBinding(
            Queue verificationRequestedQueue,
            TopicExchange notificationExchange
    ) {

        return BindingBuilder
                .bind(verificationRequestedQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.VERIFICATION_REQUESTED_ROUTING_KEY
                );
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


    /*
     * ============================================================
     * USER DELETED
     * ============================================================
     */

    @Bean
    public Queue userDeletedQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.USER_DELETED_QUEUE
                )
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

        return QueueBuilder
                .durable("user.deleted.dlq")
                .build();
    }


    @Bean
    public Binding userDeletedBinding(
            Queue userDeletedQueue,
            TopicExchange notificationExchange
    ) {

        return BindingBuilder
                .bind(userDeletedQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.USER_DELETED_ROUTING_KEY
                );
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


    /*
     * ============================================================
     * PASSWORD RESET
     * ============================================================
     */

    @Bean
    public Queue passwordResetQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.PASSWORD_RESET_QUEUE
                )
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

        return QueueBuilder
                .durable("password.reset.dlq")
                .build();
    }


    @Bean
    public Binding passwordResetBinding(
            Queue passwordResetQueue,
            TopicExchange notificationExchange
    ) {

        return BindingBuilder
                .bind(passwordResetQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.PASSWORD_RESET_ROUTING_KEY
                );
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


    /*
     * ============================================================
     * REVIEW EXCHANGE
     * ============================================================
     */

    @Bean
    public TopicExchange reviewExchange() {

        return ExchangeBuilder
                .topicExchange(REVIEW_EXCHANGE)
                .durable(true)
                .build();
    }


    /*
     * ============================================================
     * REVIEW DEAD LETTER EXCHANGE
     * ============================================================
     */

    @Bean
    public DirectExchange reviewDeadLetterExchange() {

        return ExchangeBuilder
                .directExchange(REVIEW_DLX)
                .durable(true)
                .build();
    }


    /*
     * ============================================================
     * COMMON REVIEW QUEUE BUILDER
     * ============================================================
     */

    private Queue buildReviewQueue(String queueName) {

        return QueueBuilder
                .durable(queueName)

                .withArgument(
                        "x-dead-letter-exchange",
                        REVIEW_DLX
                )

                .withArgument(
                        "x-dead-letter-routing-key",
                        REVIEW_DL_ROUTING_KEY
                )

                .build();
    }


    /*
     * ============================================================
     * REVIEW QUEUES
     * ============================================================
     */

    @Bean
    public Queue reviewerAssignedQueue() {

        return buildReviewQueue(
                REVIEW_ASSIGNMENT_QUEUE
        );
    }


    @Bean
    public Queue reviewAcceptedQueue() {

        return buildReviewQueue(
                REVIEW_ACCEPTED_QUEUE
        );
    }


    @Bean
    public Queue reviewDeclinedQueue() {

        return buildReviewQueue(
                REVIEW_DECLINED_QUEUE
        );
    }


    @Bean
    public Queue reviewSubmittedQueue() {

        return buildReviewQueue(
                REVIEW_SUBMITTED_QUEUE
        );
    }


    @Bean
    public Queue decisionQueue() {

        return buildReviewQueue(
                REVIEW_DECISION_QUEUE
        );
    }


    @Bean
    public Queue reminderQueue() {

        return buildReviewQueue(
                REVIEW_REMINDER_QUEUE
        );
    }


    @Bean
    public Queue revisionQueue() {

        return buildReviewQueue(
                REVIEW_REVISION_QUEUE
        );
    }


    @Bean
    public Queue acceptedPaperQueue() {

        return buildReviewQueue(
                REVIEW_ACCEPTED_PAPER_QUEUE
        );
    }


    @Bean
    public Queue reviewEscalationQueue() {

        return buildReviewQueue(
                REVIEW_ESCALATION_QUEUE
        );
    }


    /*
     * ============================================================
     * REVIEW BINDINGS
     * ============================================================
     */

    @Bean
    public Binding reviewerAssignedBinding(
            Queue reviewerAssignedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reviewerAssignedQueue)
                .to(reviewExchange)
                .with("review.assignment");
    }


    @Bean
    public Binding acceptedBinding(
            Queue reviewAcceptedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reviewAcceptedQueue)
                .to(reviewExchange)
                .with("review.accepted");
    }


    @Bean
    public Binding declinedBinding(
            Queue reviewDeclinedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reviewDeclinedQueue)
                .to(reviewExchange)
                .with("review.declined");
    }


    @Bean
    public Binding submittedBinding(
            Queue reviewSubmittedQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reviewSubmittedQueue)
                .to(reviewExchange)
                .with("review.submitted");
    }


    @Bean
    public Binding decisionBinding(
            Queue decisionQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(decisionQueue)
                .to(reviewExchange)
                .with("review.decision");
    }


    @Bean
    public Binding reminderBinding(
            Queue reminderQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reminderQueue)
                .to(reviewExchange)
                .with("review.reminder");
    }


    @Bean
    public Binding revisionBinding(
            Queue revisionQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(revisionQueue)
                .to(reviewExchange)
                .with("review.revision");
    }


    @Bean
    public Binding acceptedPaperBinding(
            Queue acceptedPaperQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(acceptedPaperQueue)
                .to(reviewExchange)
                .with(
                        "review.accepted.paper"
                );
    }


    @Bean
    public Binding reviewEscalationBinding(
            Queue reviewEscalationQueue,
            TopicExchange reviewExchange
    ) {

        return BindingBuilder
                .bind(reviewEscalationQueue)
                .to(reviewExchange)
                .with(
                        RabbitMQConstants.REVIEW_ESCALATION_ROUTING_KEY
                );
    }


    /*
     * ============================================================
     * REVIEW DEAD LETTER QUEUE
     * ============================================================
     */

    @Bean
    public Queue reviewDeadLetterQueue() {

        return QueueBuilder
                .durable(REVIEW_DLQ)
                .build();
    }


    @Bean
    public Binding reviewDeadLetterBinding(
            Queue reviewDeadLetterQueue,
            DirectExchange reviewDeadLetterExchange
    ) {

        return BindingBuilder
                .bind(reviewDeadLetterQueue)
                .to(reviewDeadLetterExchange)
                .with(REVIEW_DL_ROUTING_KEY);
    }


    /*
     * ============================================================
     * MESSAGE CONVERTER
     * ============================================================
     */

    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }


    /*
     * ============================================================
     * RETRY INTERCEPTOR
     * ============================================================
     */

    @Bean
    public StatelessRetryOperationsInterceptor retryInterceptor() {

        return RetryInterceptorBuilder
                .stateless()

                /*
                 * Spring AMQP 4.x:
                 *
                 * maxRetries(3)
                 *
                 * means three retries in addition to
                 * the initial delivery.
                 */
                .maxRetries(3)

                .backOffOptions(
                        2000,
                        2.0,
                        10000
                )

                .recoverer(
                        new RejectAndDontRequeueRecoverer()
                )

                .build();
    }


    /*
     * ============================================================
     * LISTENER CONTAINER FACTORY
     * ============================================================
     */

    @Bean
    public SimpleRabbitListenerContainerFactory
    rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter,
            StatelessRetryOperationsInterceptor retryInterceptor
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(
                connectionFactory
        );

        factory.setMessageConverter(
                messageConverter
        );

        factory.setAdviceChain(
                retryInterceptor
        );

        /*
         * When listener processing ultimately fails,
         * don't requeue indefinitely.
         */
        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}
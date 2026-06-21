package com.example.notification_service.service;

import com.example.notification_service.dto.event.*;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.gateway-url}")
    private String gatewayUrl;

    // =====================================================
    // USER REGISTERED
    // =====================================================

    @Override
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendWelcomeEmail(
            UserRegisteredEvent event
    ) {

        Context context = new Context();

        context.setVariable(
                "name",
                event.firstName()
        );

        context.setVariable(
                "verificationLink",
                gatewayUrl +
                        "/auth/verify-email?token=" +
                        event.verificationToken()
        );

        String html = templateEngine.process(
                "welcome-email",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Verify Your Account",
                html
        );

        log.info(
                "Verification email sent to {}",
                event.email()
        );
    }

    @Recover
    public void recoverWelcomeEmail(
            Exception ex,
            UserRegisteredEvent event
    ) {

        log.error(
                "Verification email permanently failed for {}",
                event.email(),
                ex
        );
    }

    // =====================================================
    // RESEND VERIFICATION EMAIL
    // =====================================================

    @Override
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendVerificationEmail(
            VerificationEmailRequestedEvent event
    ) {

        Context context = new Context();

        context.setVariable(
                "name",
                event.firstName()
        );

        context.setVariable(
                "verificationLink",
                gatewayUrl +
                        "/auth/verify-email?token=" +
                        event.verificationToken()
        );

        String html = templateEngine.process(
                "verification-requested",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Verify Your ResearchHub Account",
                html
        );

        log.info(
                "Verification email resent to {}",
                event.email()
        );
    }

    @Recover
    public void recoverVerificationEmail(
            Exception ex,
            VerificationEmailRequestedEvent event
    ) {

        log.error(
                "Verification resend permanently failed for {}",
                event.email(),
                ex
        );
    }

    // =====================================================
    // USER VERIFIED
    // =====================================================

    @Override
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendVerifiedEmail(
            UserVerifiedEvent event
    ) {

        Context context = new Context();

        context.setVariable(
                "name",
                event.firstName()
        );

        String html = templateEngine.process(
                "verification-email",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Welcome To ResearchHub",
                html
        );

        log.info(
                "Welcome email sent to {}",
                event.email()
        );
    }

    @Recover
    public void recoverVerifiedEmail(
            Exception ex,
            UserVerifiedEvent event
    ) {

        log.error(
                "Welcome email permanently failed for {}",
                event.email(),
                ex
        );
    }

    // =====================================================
    // PASSWORD RESET
    // =====================================================

    @Override
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendPasswordResetEmail(
            PasswordResetRequestedEvent event
    ) {

        Context context = new Context();

        context.setVariable(
                "firstName",
                event.firstName()
        );

        context.setVariable(
                "token",
                event.token()
        );

        context.setVariable(
                "expiry",
                "1 Hour"
        );

        String html = templateEngine.process(
                "password-reset-email",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Password Reset Request",
                html
        );

        log.info(
                "Password reset email sent to {}",
                event.email()
        );
    }

    @Recover
    public void recoverPasswordResetEmail(
            Exception ex,
            PasswordResetRequestedEvent event
    ) {

        log.error(
                "Password reset email permanently failed for {}",
                event.email(),
                ex
        );
    }

    // =====================================================
    // USER DELETED
    // =====================================================

    @Override
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendGoodbyeEmail(
            UserDeletedEvent event
    ) {

        Context context = new Context();

        context.setVariable(
                "firstName",
                event.firstName()
        );

        String html = templateEngine.process(
                "goodbye-email",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Sorry To See You Go",
                html
        );

        log.info(
                "Goodbye email sent to {}",
                event.email()
        );
    }

    @Recover
    public void recoverGoodbyeEmail(
            Exception ex,
            UserDeletedEvent event
    ) {

        log.error(
                "Goodbye email permanently failed for {}",
                event.email(),
                ex
        );
    }

    // =====================================================
    // SHARED EMAIL METHOD
    // =====================================================

    private void sendHtmlEmail(
            String to,
            String subject,
            String html
    ) {

        try {

            MimeMessage mimeMessage =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mimeMessage,
                            true,
                            StandardCharsets.UTF_8.name()
                    );

            helper.setFrom(senderEmail);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(
                    html,
                    true
            );

            mailSender.send(
                    mimeMessage
            );

        } catch (Exception ex) {

            log.error(
                    "Failed sending email to {}",
                    to,
                    ex
            );

            throw new RuntimeException(
                    "Failed to send email",
                    ex
            );
        }
    }
}
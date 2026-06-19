package com.example.notification_service.service;

import com.example.notification_service.dto.event.*;
import jakarta.mail.MessagingException;
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


 // WELCOME EMAIL
    @Override
    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    public void sendWelcomeEmail(
            UserRegisteredEvent event
    ) {

        Context context = new Context();

        context.setVariable("name", event.firstName());

        context.setVariable(
                "verificationLink",
                gatewayUrl + "/auth/verify-email?token=" + event.verificationToken()
        );

        String html = templateEngine.process(
                "verification-email", context
        );

        sendHtmlEmail(
                event.email(),
                "Verify Your Account",
                html
        );
    }
    @Recover
    public void recoverWelcomeEmail(
            Exception ex,
            UserRegisteredEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    //SEND VERIFICATION
    @Override
    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    public void sendVerificationEmail(
            VerificationEmailRequestedEvent event
    ) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    StandardCharsets.UTF_8.name()
            );

            Context context = new Context();

            String verificationLink = gatewayUrl +
                    "/auth/verify-email?token=" +
                    event.verificationToken();

            context.setVariable("firstName", event.firstName());

            context.setVariable("verificationLink", verificationLink);

            String html = templateEngine.process(
                    "verification-email",
                    context
            );

            helper.setTo(event.email());
            helper.setSubject("Verify Your ResearchHub Account");

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            log.info("Verification email sent to {}", event.email());

        } catch (Exception ex) {

            log.error("Failed verification email to {}", event.email(), ex);

            throw ex;
        }
    }
    @Recover
    public void recoverVerificationEmail(
            Exception ex,
            VerificationEmailRequestedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    @Override
    public void sendVerifiedEmail(UserVerifiedEvent event) {

        Context context = new Context();

        context.setVariable("name", event.firstName());

        String html = templateEngine.process(
                "welcome-email", context
        );

        sendHtmlEmail(
                event.email(),
                "Welcome To ResearchHub",
                html
        );

    }
    @Recover
    public void recoverVerifiedEmail(
            Exception ex,
            UserVerifiedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Override
    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    public void sendPasswordResetEmail(
            PasswordResetRequestedEvent event
    ) {
        Context context = new Context();

        context.setVariable("token", event.token());

        String html = templateEngine.process(
                "password-reset-email",
                context
        );

        sendHtmlEmail(
                event.email(),
                "Password Reset Request",
                html
        );
    }
    @Recover
    public void recoverPasswordResetEmail(
            Exception ex,
            PasswordResetRequestedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    @Override
    @Retryable(
            retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3000)
    )
    public void sendGoodbyeEmail(
            UserDeletedEvent event
    ) throws MessagingException {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    StandardCharsets.UTF_8.name()
            );

            Context context = new Context();

            context.setVariable(
                    "firstName",
                    event.firstName()
            );

            String html = templateEngine.process(
                    "goodbye-email", context
            );

            helper.setTo(event.email());

            helper.setSubject("Sorry To See You Go");

            helper.setText(html, true);

            mailSender.send(mimeMessage);

            log.info(
                    "Goodbye email sent to {}",
                    event.email()
            );

        } catch (Exception ex) {

            log.error(
                    "Failed goodbye email {}",
                    event.email(),
                    ex
            );

            throw ex;
        }
    }
    @Recover
    public void recoverGoodbyeEmail(
            Exception ex,
            UserDeletedEvent event
    ) {
        log.error("Email failed permanently {}", event.email(), ex);
    }

    private void sendHtmlEmail(
            String to,
            String subject,
            String html
    ) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom(senderEmail);

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(html, true);

            mailSender.send(mimeMessage);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to send email", ex);
        }
    }
}
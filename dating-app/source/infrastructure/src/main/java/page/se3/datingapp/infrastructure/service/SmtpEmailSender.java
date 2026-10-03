package page.se3.datingapp.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.auth.interfaces.EmailSender;
import page.se3.datingapp.domain.config.app.email.credentials.EmailCredentialsConfig;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailSendingFailed;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private final EmailCredentialsConfig emailCredentialsConfig;
    private final JavaMailSenderImpl mailSender;

    @Override
    public void send(String email, String topic, String contents) throws EmailSendingFailed {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailCredentialsConfig.fromEmail());
        message.setTo(email);
        message.setSubject(topic);
        message.setText(contents);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            log.warn("Failed to send email to: {}", email, e);
            throw new EmailSendingFailed();
        }
    }
}

package page.se3.datingapp.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.auth.interfaces.EmailSender;
import page.se3.datingapp.domain.config.app.email.credentials.EmailCredentialsConfig;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpEmailSender implements EmailSender {

    private final EmailCredentialsConfig emailCredentialsConfig;
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendAsync(String email, String topic, String contents) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailCredentialsConfig.fromEmail());
        message.setTo(email);
        message.setSubject(topic);
        message.setText(contents);

        mailSender.send(message);
    }
}

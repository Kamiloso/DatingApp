package page.se3.datingapp.infrastructure.config.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import page.se3.datingapp.domain.config.app.email.credentials.EmailCredentialsConfig;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailCredentialsConfig emailCredentialsConfig;

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailCredentialsConfig.host());
        mailSender.setPort(emailCredentialsConfig.port());

        boolean hasAuth = !emailCredentialsConfig.username().isEmpty();

        if (hasAuth) {
            mailSender.setUsername(emailCredentialsConfig.username());
            mailSender.setPassword(emailCredentialsConfig.password());
        }

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(hasAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(hasAuth));

        return mailSender;
    }
}

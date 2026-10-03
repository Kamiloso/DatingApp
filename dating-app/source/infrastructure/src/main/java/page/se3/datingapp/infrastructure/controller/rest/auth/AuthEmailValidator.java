package page.se3.datingapp.infrastructure.controller.rest.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.config.app.auth.AuthConfig;
import page.se3.datingapp.domain.core.struct.Lazy;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailRegexMismatch;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthEmailValidator {

    private final AuthConfig authConfig;

    private final Lazy<Pattern> lazyEmailPattern = Lazy.of(() -> {
        var self = this;
        return Pattern.compile(self.authConfig.allowedEmailRegex());
    });

    public String checkEmail(String email) throws EmailRegexMismatch {
        if (!lazyEmailPattern.get().matcher(email).find()) {
            throw new EmailRegexMismatch();
        }
        return email;
    }
}

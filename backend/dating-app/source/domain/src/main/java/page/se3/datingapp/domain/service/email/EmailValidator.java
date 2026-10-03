package page.se3.datingapp.domain.service.email;

import page.se3.datingapp.domain.Final;
import page.se3.datingapp.domain.annotation.DomainService;
import page.se3.datingapp.domain.config.app.auth.AuthConfig;
import page.se3.datingapp.domain.core.annotation.MayBeNull;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailValidationFailed;

import java.util.regex.Pattern;

@DomainService
public class EmailValidator {

    private final Pattern emailPattern;

    public EmailValidator(AuthConfig authConfig) {
        emailPattern = Pattern.compile(authConfig.allowedEmailRegex());
    }

    public boolean isEmailValid(@MayBeNull String email) {
        return email != null && email.length() <= Final.LEN_255 && emailPattern.matcher(email).find();
    }

    public String checkEmail(@MayBeNull String email) throws EmailValidationFailed {
        if (!isEmailValid(email)) {
            throw new EmailValidationFailed();
        }
        return email;
    }
}

package page.se3.datingapp.infrastructure.config.security.identity;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.service.user.account.UserAccountService;
import page.se3.datingapp.domain.config.AuthConfig;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.AuthorizationFailed;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.Template;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class IdentityChecker {

    private final UserAccountService userAccountService;
    private final Pattern emailPattern;

    public IdentityChecker(AuthConfig authConfig, UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        this.emailPattern = Pattern.compile(authConfig.allowedEmailRegex());
    }

    public String getEmail(Jwt jwt) throws AuthorizationFailed {
        return Optional.ofNullable(jwt.getClaimAsString("email"))
                .filter(email -> email.length() <= Template.LEN_255)
                .filter(email -> emailPattern.matcher(email).find())
                .orElseThrow(AuthorizationFailed::new);
    }

    public AuthKey getAuthKey(Jwt jwt) throws AuthorizationFailed, AccountNotFound {
        return getAuthKey(getEmail(jwt));
    }

    public AuthKey getAuthKey(String email) throws AccountNotFound {
        return userAccountService.getAuthKey(email);
    }
}

package page.se3.datingapp.infrastructure.config.security.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.service.user.account.UserAccountService;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.AuthorizationFailed;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.Template;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdentityChecker {

    private final UserAccountService userAccountService;

    public String getEmail(Jwt jwt) throws AuthorizationFailed {
        return Optional.ofNullable(jwt.getClaimAsString("email"))
                .filter(email -> email.length() <= Template.LEN_255)
                .orElseThrow(AuthorizationFailed::new);
    }

    public AuthKey getAuthKey(Jwt jwt) throws AuthorizationFailed, AccountNotFound {
        return getAuthKey(getEmail(jwt));
    }

    public AuthKey getAuthKey(String email) throws AccountNotFound {
        return userAccountService.getAuthKey(email);
    }
}

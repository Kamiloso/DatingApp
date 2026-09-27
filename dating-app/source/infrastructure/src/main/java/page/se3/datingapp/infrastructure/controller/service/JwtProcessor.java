package page.se3.datingapp.infrastructure.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.application.service.user.account.UserAccountService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtProcessor {

    private final UserAccountService userAccountService;

    public String getEmail(Jwt jwt) {
        return Objects.requireNonNull(jwt.getClaimAsString("email"));
    }

    public UserKey getUserKey(Jwt jwt) throws AccountNotFound {
        return userAccountService.get(getEmail(jwt)).getUserKey();
    }
}

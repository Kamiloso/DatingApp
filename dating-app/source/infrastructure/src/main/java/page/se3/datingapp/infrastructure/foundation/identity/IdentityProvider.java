package page.se3.datingapp.infrastructure.foundation.identity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.service.email.EmailValidator;
import page.se3.datingapp.application.service.user.UserAccountService;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.internal.AuthorizationFailed;
import page.se3.datingapp.infrastructure.foundation.identity.helper.IdentityParam;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdentityProvider {

    private final UserAccountService userAccountService;
    private final EmailValidator emailValidator;
    private final JwtDecoder jwtDecoder;
    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    public Object resolveIdentity(IdentityParam identityParam, HttpServletRequest httpServletRequest)
            throws AccountNotFound, AuthorizationFailed {
        String token = Optional.ofNullable(httpServletRequest)
                .map(bearerTokenResolver::resolve)
                .orElseThrow(AuthorizationFailed::new);

        Jwt jwt = decodeJwt(token);

        String email = Optional.ofNullable(jwt.getClaimAsString("email"))
                .filter(emailValidator::isEmailValid)
                .orElseThrow(AuthorizationFailed::new);

        if (identityParam.usesEmail()) {
            return email;
        }

        if (identityParam.usesAuthKey()) {
            return userAccountService.getAuthKey(email);
        }

        throw new IllegalStateException("Cannot resolve identity");
    }

    private Jwt decodeJwt(String token) throws AuthorizationFailed {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new AuthorizationFailed();
        }
    }
}

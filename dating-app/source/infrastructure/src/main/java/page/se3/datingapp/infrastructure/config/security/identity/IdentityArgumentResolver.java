package page.se3.datingapp.infrastructure.config.security.identity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.AuthorizationFailed;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdentityArgumentResolver implements HandlerMethodArgumentResolver {

    private final IdentityChecker identityChecker;
    private final JwtDecoder jwtDecoder;
    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return new IdentityParam(parameter).isValid();
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws AuthorizationFailed, AccountNotFound {
        IdentityParam identityParam = new IdentityParam(parameter);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = Optional.ofNullable(request)
                .map(bearerTokenResolver::resolve)
                .orElseThrow(AuthorizationFailed::new);

        Jwt jwt = decodeJwt(token);

        if (identityParam.usesEmail()) {
            return identityChecker.getEmail(jwt);
        }

        if (identityParam.usesAuthKey()) {
            return identityChecker.getAuthKey(jwt);
        }

        throw new IllegalStateException();
    }

    private Jwt decodeJwt(String token) throws AuthorizationFailed {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new AuthorizationFailed();
        }
    }
}

package page.se3.datingapp.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import page.se3.datingapp.infrastructure.abort.reason.AuthorizationFailed;

@Component
@RequiredArgsConstructor
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtDecoder jwtDecoder;
    private final BearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Jwt.class) &&
                parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            throw new AuthorizationFailed();
        }

        String token = bearerTokenResolver.resolve(request);
        if (token == null) {
            throw new AuthorizationFailed();
        }

        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new AuthorizationFailed();
        }
    }
}

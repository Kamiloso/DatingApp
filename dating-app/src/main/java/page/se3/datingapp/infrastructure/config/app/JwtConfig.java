package page.se3.datingapp.infrastructure.config.app;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import page.se3.datingapp.domain.exception.abort.reason.AuthorizationFailed;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JwtConfig implements WebMvcConfigurer {

    private final JwtDecoder jwtDecoder;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtArgumentResolver(jwtDecoder));
    }

    @RequiredArgsConstructor
    private static class JwtArgumentResolver implements HandlerMethodArgumentResolver {

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
        ) throws AuthorizationFailed {
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
}

package page.se3.datingapp.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import page.se3.datingapp.infrastructure.config.security.identity.IdentityArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthorizationConfig implements WebMvcConfigurer {

    private final IdentityArgumentResolver identityArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(identityArgumentResolver);
    }
}

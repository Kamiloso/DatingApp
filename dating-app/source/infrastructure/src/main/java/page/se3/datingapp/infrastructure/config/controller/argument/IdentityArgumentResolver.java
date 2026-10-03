package page.se3.datingapp.infrastructure.config.controller.argument;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.internal.AuthorizationFailed;
import page.se3.datingapp.infrastructure.foundation.identity.IdentityProvider;
import page.se3.datingapp.infrastructure.foundation.identity.helper.IdentityParam;

@Configuration
@RequiredArgsConstructor
public class IdentityArgumentResolver implements HandlerMethodArgumentResolver {

    private final IdentityProvider identityProvider;

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return new IdentityParam(parameter).isValid();
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws AuthorizationFailed, AccountNotFound {
        IdentityParam identityParam = new IdentityParam(parameter);
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if (!identityParam.isValid()) {
            throw new IllegalStateException("Invalid identity param");
        }

        return identityProvider.resolveIdentity(identityParam, httpServletRequest);
    }
}

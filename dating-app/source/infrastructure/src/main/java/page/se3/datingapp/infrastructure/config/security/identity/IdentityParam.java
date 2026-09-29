package page.se3.datingapp.infrastructure.config.security.identity;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.core.MethodParameter;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;

@Getter
@Accessors(fluent = true)
public class IdentityParam {

    private final boolean usesEmail;
    private final boolean usesAuthKey;
    private final boolean isValid;

    public IdentityParam(MethodParameter parameter) {
        boolean isAnnotated = parameter.hasParameterAnnotation(AuthIdentity.class);
        Class<?> paramType = parameter.getParameterType();

        usesEmail = isAnnotated && String.class.equals(paramType);
        usesAuthKey = isAnnotated && AuthKey.class.equals(paramType);

        isValid = usesEmail || usesAuthKey;
    }
}

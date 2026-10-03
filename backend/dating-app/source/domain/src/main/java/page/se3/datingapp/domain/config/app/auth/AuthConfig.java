package page.se3.datingapp.domain.config.app.auth;

import page.se3.datingapp.domain.config.app.auth.jwt.AuthJwtConfig;
import page.se3.datingapp.domain.config.app.auth.verificationcode.AuthVerificationCodeConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;

public record AuthConfig(
        String allowedEmailRegex,
        AuthJwtConfig jwt,
        AuthVerificationCodeConfig verificationCode
) implements ConfigBean {}

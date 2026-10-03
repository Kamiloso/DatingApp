package page.se3.datingapp.domain.config.app.auth.verificationcode;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record AuthVerificationCodeConfig(
        Long expirationMillis
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return expirationMillis >= 0;
    }
}

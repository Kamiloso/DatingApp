package page.se3.datingapp.domain.config.app.auth.jwt;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record AuthJwtConfig(
        String issuer,
        Long expirationMillis
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return expirationMillis >= 0;
    }
}

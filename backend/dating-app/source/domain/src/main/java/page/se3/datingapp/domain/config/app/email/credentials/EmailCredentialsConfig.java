package page.se3.datingapp.domain.config.app.email.credentials;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record EmailCredentialsConfig(
        String host,
        Integer port,
        String username,
        String password,
        String fromEmail
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return port >= 1 && port <= 65536;
    }
}

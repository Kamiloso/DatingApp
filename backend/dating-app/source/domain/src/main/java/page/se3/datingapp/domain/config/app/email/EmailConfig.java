package page.se3.datingapp.domain.config.app.email;

import page.se3.datingapp.domain.config.app.email.credentials.EmailCredentialsConfig;
import page.se3.datingapp.domain.config.app.email.message.EmailMessageConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;

public record EmailConfig(
        EmailCredentialsConfig credentials,
        EmailMessageConfig message
) implements ConfigBean {}

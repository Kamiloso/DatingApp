package page.se3.datingapp.domain.config.app.email.message;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record EmailMessageConfig(
        String topic,
        String contents
) implements ConfigBean {}

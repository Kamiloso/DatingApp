package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record AppConfig(
        String apiVersion,
        String startMessage,
        AuthConfig auth,
        RetryConfig retry,
        LimitConfig limits,
        MatchingConfig matching,
        QuizConfig quiz
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {apiVersion, startMessage, auth, retry, limits, matching, quiz};
    }
}

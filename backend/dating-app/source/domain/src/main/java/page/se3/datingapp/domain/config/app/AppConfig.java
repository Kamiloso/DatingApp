package page.se3.datingapp.domain.config.app;

import page.se3.datingapp.domain.config.app.auth.AuthConfig;
import page.se3.datingapp.domain.config.app.email.EmailConfig;
import page.se3.datingapp.domain.config.app.limiting.LimitingConfig;
import page.se3.datingapp.domain.config.app.matching.MatchingConfig;
import page.se3.datingapp.domain.config.app.quiz.QuizConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;

public record AppConfig(
        String applicationDisplayName,
        String startMessage,
        Boolean isDevelopmentMode,
        AuthConfig auth,
        EmailConfig email,
        LimitingConfig limiting,
        MatchingConfig matching,
        QuizConfig quiz
) implements ConfigBean {}

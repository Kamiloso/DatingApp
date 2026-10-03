package page.se3.datingapp.domain.config.app.quiz;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record QuizConfig(
        Integer surveyVersion
        // TODO: make more quiz information
) implements ConfigBean {}

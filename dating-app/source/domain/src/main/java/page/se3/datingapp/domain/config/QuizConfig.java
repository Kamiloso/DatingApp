package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record QuizConfig(
        Integer readyIndex
        // TODO: make more quiz information
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {readyIndex};
    }
}

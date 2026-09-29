package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record AuthConfig(
        String allowedEmailRegex
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {allowedEmailRegex};
    }
}

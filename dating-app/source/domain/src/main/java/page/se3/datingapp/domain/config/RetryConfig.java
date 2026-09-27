package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record RetryConfig(
        Integer maxRetries,
        Integer initialSleepMillis,
        Double sleepTimeMultiplier
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {maxRetries, initialSleepMillis, sleepTimeMultiplier};
    }

    @Override
    public boolean isValid() {
        return maxRetries >= 0 && initialSleepMillis >= 0 && sleepTimeMultiplier >= 0.0;
    }
}

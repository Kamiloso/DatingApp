package page.se3.datingapp.domain.config.app.limiting.retry;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record RetryConfig(
        Integer maxRetries,
        Integer initialSleepMillis,
        Double sleepTimeMultiplier
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return maxRetries >= 0 && initialSleepMillis >= 0 && sleepTimeMultiplier >= 0.0;
    }
}

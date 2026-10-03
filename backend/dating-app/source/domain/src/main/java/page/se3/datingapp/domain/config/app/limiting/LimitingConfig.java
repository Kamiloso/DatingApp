package page.se3.datingapp.domain.config.app.limiting;

import page.se3.datingapp.domain.config.app.limiting.capacity.CapacityConfig;
import page.se3.datingapp.domain.config.app.limiting.retry.RetryConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;

public record LimitingConfig(
        CapacityConfig capacity,
        RetryConfig retry
) implements ConfigBean {}

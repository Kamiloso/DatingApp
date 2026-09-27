package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record LimitConfig(
        Integer maxBatchSize,
        Integer maxListSize
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {maxBatchSize, maxListSize};
    }

    @Override
    public boolean isValid() {
        return maxBatchSize >= 1 && maxListSize >= 1;
    }
}

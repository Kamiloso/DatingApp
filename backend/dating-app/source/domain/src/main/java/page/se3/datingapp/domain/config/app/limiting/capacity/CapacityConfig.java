package page.se3.datingapp.domain.config.app.limiting.capacity;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record CapacityConfig(
        Integer maxBatchSize,
        Integer maxListSize
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return maxBatchSize >= 1 && maxListSize >= 1;
    }
}

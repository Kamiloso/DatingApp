package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record MatchingConfig(
        Integer matchCountLimit,
        Integer maxAgeDifference,
        MatchingScheduleConfig schedule
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {matchCountLimit, maxAgeDifference, schedule};
    }

    @Override
    public boolean isValid() {
        return matchCountLimit >= 0 && maxAgeDifference >= 0;
    }
}

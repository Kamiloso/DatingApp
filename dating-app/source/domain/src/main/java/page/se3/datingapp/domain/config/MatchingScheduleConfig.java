package page.se3.datingapp.domain.config;

import page.se3.datingapp.domain.config.interfaces.ConfigBean;
import page.se3.datingapp.domain.config.interfaces.Validated;

public record MatchingScheduleConfig(
        String cron,
        String timezone,
        Integer lockAtLeastForMinutes,
        Integer lockAtMostForMinutes
) implements ConfigBean, Validated {

    @Override
    public Object[] getChildren() {
        return new Object[] {cron, timezone, lockAtLeastForMinutes, lockAtMostForMinutes};
    }

    @Override
    public boolean isValid() {
        return lockAtLeastForMinutes >= 0 && lockAtLeastForMinutes <= lockAtMostForMinutes;
    }
}

package page.se3.datingapp.domain.config.app.matching.schedule;

import page.se3.datingapp.domain.config.helper.ConfigBean;

public record MatchingScheduleConfig(
        String cron,
        String timezone,
        Integer lockAtLeastForMillis,
        Integer lockAtMostForMillis
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return lockAtLeastForMillis >= 0 && lockAtLeastForMillis <= lockAtMostForMillis;
    }
}

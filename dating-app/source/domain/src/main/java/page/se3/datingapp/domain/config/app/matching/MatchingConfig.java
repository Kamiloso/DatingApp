package page.se3.datingapp.domain.config.app.matching;

import page.se3.datingapp.domain.config.app.matching.schedule.MatchingScheduleConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;

public record MatchingConfig(
        Integer matchCountLimit,
        Integer maxAgeDifference,
        MatchingScheduleConfig schedule
) implements ConfigBean {

    @Override
    public boolean areFieldsValid() {
        return matchCountLimit >= 0 && maxAgeDifference >= 0;
    }
}

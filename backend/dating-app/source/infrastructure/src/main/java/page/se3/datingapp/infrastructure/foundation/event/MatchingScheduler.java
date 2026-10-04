package page.se3.datingapp.infrastructure.foundation.event;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.interfaces.DistributedLocker;
import page.se3.datingapp.domain.config.app.matching.schedule.MatchingScheduleConfig;
import page.se3.datingapp.domain.core.util.UniqueKey;
import page.se3.datingapp.infrastructure.controller.rest.matching.MatchingProxy;

import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final String UNIQUE_KEY = UniqueKey.of(MatchingScheduler.class);

    private final MatchingScheduleConfig matchingScheduleConfig;
    private final MatchingProxy matchingProxy;
    private final DistributedLocker distributedLocker;

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        if (matchingScheduleConfig.enabled()) {
            TimeZone timeZone = TimeZone.getTimeZone(matchingScheduleConfig.timezone());

            taskRegistrar.addTriggerTask(
                    this::tryExecuteMatching,
                    new CronTrigger(matchingScheduleConfig.cron(), timeZone)
            );
        }
    }

    private void tryExecuteMatching() {
        distributedLocker.lockExecute(
                matchingProxy::runMatchingFromSchedule,
                UNIQUE_KEY,
                matchingScheduleConfig.lockAtLeastForMillis(),
                matchingScheduleConfig.lockAtMostForMillis()
        );
    }
}

package page.se3.datingapp.infrastructure.controller.listener.matching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.config.MatchingScheduleConfig;
import page.se3.datingapp.domain.core.util.ReportUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final String MATCHING_SCHEDULE_KEY = "matching-schedule";

    private final MatchingScheduleConfig matchingScheduleConfig;
    private final MatchingProxy matchingProxy;
    private final LockingTaskExecutor lockingTaskExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TimeZone timeZone = TimeZone.getTimeZone(matchingScheduleConfig.timezone());

        taskRegistrar.addTriggerTask(
                this::tryExecuteMatching,
                new CronTrigger(matchingScheduleConfig.cron(), timeZone)
        );
    }

    private void tryExecuteMatching() {
        Runnable logRunMatching = () -> ReportUtils.logRunToConsole(
                "Matching schedule",
                matchingProxy::runMatching, log::info, log::error
        );

        lockingTaskExecutor.executeWithLock(
                logRunMatching,
                new LockConfiguration(
                        Instant.now(),
                        MATCHING_SCHEDULE_KEY,
                        Duration.ofMinutes(matchingScheduleConfig.lockAtMostForMinutes()),
                        Duration.ofMinutes(matchingScheduleConfig.lockAtLeastForMinutes())
                )
        );
    }
}

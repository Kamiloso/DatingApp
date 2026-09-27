package page.se3.datingapp.infrastructure.invokers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.config.MatchingScheduleConfig;
import page.se3.datingapp.domain.core.tool.UniqueKey;
import page.se3.datingapp.infrastructure.controller.listener.matching.MatchingProxy;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final UniqueKey MATCHING_SCHEDULE_KEY = UniqueKey.register("matching-schedule");

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
        lockingTaskExecutor.executeWithLock(
                (Runnable) this::executeMatching,
                new LockConfiguration(
                        Instant.now(),
                        MATCHING_SCHEDULE_KEY.get(),
                        Duration.ofMinutes(matchingScheduleConfig.lockAtMostForMinutes()),
                        Duration.ofMinutes(matchingScheduleConfig.lockAtLeastForMinutes())
                )
        );
    }

    private void executeMatching() {
        log.info("Matching schedule has started...");
        long startTime = System.currentTimeMillis();

        try {
            matchingProxy.runMatchingSchedule();
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("Matching schedule has failed (after {}ms).", duration);
            throw e;
        }

        long duration = System.currentTimeMillis() - startTime;
        log.info("Matching schedule has finished (after {}ms).", duration);
    }
}

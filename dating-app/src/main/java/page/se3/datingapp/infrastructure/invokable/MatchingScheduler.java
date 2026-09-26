package page.se3.datingapp.infrastructure.invokable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.core.tool.UniqueKey;
import page.se3.datingapp.infrastructure.rest.controller.matching.MatchingProxy;
import page.se3.datingapp.domain.config.AppConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final UniqueKey MATCHING_SCHEDULE_KEY = UniqueKey.register("matching-schedule");

    private final AppConfig appConfig;
    private final MatchingProxy matchingProxy;
    private final LockingTaskExecutor lockingTaskExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        var cycleConfig = appConfig.matching().cycle();

        TimeZone timeZone = TimeZone.getTimeZone(cycleConfig.timezone());

        taskRegistrar.addTriggerTask(
                this::tryExecuteMatching,
                new CronTrigger(cycleConfig.cron(), timeZone)
        );
    }

    private void tryExecuteMatching() {
        var cycleConfig = appConfig.matching().cycle();

        lockingTaskExecutor.executeWithLock(
                (Runnable) this::executeMatching,
                new LockConfiguration(
                        Instant.now(),
                        MATCHING_SCHEDULE_KEY.get(),
                        Duration.ofMinutes(cycleConfig.lockAtMostForMinutes()),
                        Duration.ofMinutes(cycleConfig.lockAtLeastForMinutes())
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

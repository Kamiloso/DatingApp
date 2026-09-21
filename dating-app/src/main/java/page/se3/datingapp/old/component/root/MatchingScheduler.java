package page.se3.datingapp.old.component.root;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.old.infrastructure.config.AppConfig;
import page.se3.datingapp.old.core.StringKey;
import page.se3.datingapp.old.onion.service.matching.MatchingService;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final StringKey SCHEDULE_LOCK = StringKey.create("matching-lock");

    private final AppConfig appConfig;
    private final MatchingService matchingService;
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
                        SCHEDULE_LOCK.get(),
                        Duration.ofMinutes(cycleConfig.lockAtMostForMinutes()),
                        Duration.ofMinutes(cycleConfig.lockAtLeastForMinutes())
                )
        );
    }

    private void executeMatching() {
        log.info("Matching schedule has started...");
        matchingService.runMatchingSchedule();
        log.info("Matching schedule has finished successfully.");
    }
}

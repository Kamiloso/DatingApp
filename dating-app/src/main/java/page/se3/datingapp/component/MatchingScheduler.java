package page.se3.datingapp.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.core.StringKey;
import page.se3.datingapp.service.matching.MatchingService;

import java.time.Duration;
import java.time.Instant;
import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final StringKey STRING_KEY = StringKey.derive(MatchingScheduler.class);

    private final AppConfig appConfig;
    private final MatchingService matchingService;
    private final LockProvider lockProvider;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        AppConfig.Matching.Cycle cycleConfig = appConfig.matching().cycle();

        TimeZone timeZone = TimeZone.getTimeZone(cycleConfig.timezone());

        taskRegistrar.addTriggerTask(
                this::tryExecuteMatching,
                new CronTrigger(cycleConfig.cron(), timeZone)
        );
    }

    private void tryExecuteMatching() {
        AppConfig.Matching.Cycle cycleConfig = appConfig.matching().cycle();

        LockingTaskExecutor executor = new DefaultLockingTaskExecutor(lockProvider);

        executor.executeWithLock(
                (Runnable) this::executeMatching,
                new LockConfiguration(
                        Instant.now(),
                        STRING_KEY.get(),
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

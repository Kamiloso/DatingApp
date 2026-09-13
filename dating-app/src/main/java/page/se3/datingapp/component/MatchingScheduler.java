package page.se3.datingapp.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import page.se3.datingapp.infrastructure.abort.RetryLoop;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.repository.redis.RedisRepository;
import page.se3.datingapp.service.matching.MatchingService;

import java.util.TimeZone;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingScheduler implements SchedulingConfigurer {

    private static final String REDIS_LOCK = "mts::lock";

    private final AppConfig appConfig;
    private final MatchingService matchingService;
    private final RedisRepository redisRepository;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        AppConfig.Matching.Cycle cycleConfig = appConfig.matching().cycle();
        TimeZone timeZone = TimeZone.getTimeZone(cycleConfig.timezone());

        taskRegistrar.addTriggerTask(
                this::tryExecuteMatching,
                new CronTrigger(cycleConfig.cron(), timeZone)
        );

        taskRegistrar.addTriggerTask(
                this::releaseLock,
                new CronTrigger(cycleConfig.cleanupCron(), timeZone)
        );
    }

    private void tryExecuteMatching() {
        AppConfig.Matching.Retry retryConfig = appConfig.matching().retry();

        if (redisRepository.add(REDIS_LOCK, "X")) {
            boolean success = RetryLoop.run(
                    matchingService::runMatchingSchedule,
                    retryConfig.cooldownMilliseconds(),
                    retryConfig.maxRetries()
            );

            if (success) {
                log.info("Matching schedule has finished successfully.");
            } else {
                log.error(
                        "Matching schedule has failed to retry {} times and has been aborted.",
                        retryConfig.maxRetries()
                );
            }
        }
    }

    private void releaseLock() {
        redisRepository.delete(REDIS_LOCK);
    }
}

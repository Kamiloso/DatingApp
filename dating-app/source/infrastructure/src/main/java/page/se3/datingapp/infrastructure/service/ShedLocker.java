package page.se3.datingapp.infrastructure.service;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.interfaces.DistributedLocker;
import page.se3.datingapp.domain.core.util.UniqueKey;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ShedLocker implements DistributedLocker {

    private static final String UNIQUE_KEY = UniqueKey.of(ShedLocker.class);

    private final LockingTaskExecutor lockingTaskExecutor;

    public void lockExecute(Runnable runnable, String key, long lockAtLeastForMillis, long lockAtMostForMillis) {
        lockingTaskExecutor.executeWithLock(
                runnable,
                new LockConfiguration(
                        Instant.now(),
                        UniqueKey.merge(UNIQUE_KEY, key),
                        Duration.ofMillis(lockAtMostForMillis),
                        Duration.ofMillis(lockAtLeastForMillis)
                )
        );
    }
}

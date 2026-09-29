package page.se3.datingapp.domain.core.util;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ReportUtils {

    private ReportUtils() {}

    public static <R> R logRunToConsole(
            String subject,
            Supplier<R> supplier,
            BiConsumer<String, Object[]> logInfo,
            BiConsumer<String, Object[]> logError
    ) {
        long startTime = System.currentTimeMillis();
        logInfo.accept(subject + " has started...", new Object[] {});

        R result;

        try {
            result = supplier.get();
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logError.accept(subject + " has failed (after {}ms).", new Object[] {duration});

            throw e;
        }

        long duration = System.currentTimeMillis() - startTime;
        logInfo.accept(subject + " has finished (after {}ms).", new Object[] {duration});

        return result;
    }

    public static void logRunToConsole(
            String subject,
            Runnable runnable,
            BiConsumer<String, Object[]> logInfo,
            BiConsumer<String, Object[]> logError
    ) {
        logRunToConsole(
                subject,
                () -> {
                    runnable.run();
                    return null;
                },
                logInfo, logError
        );
    }
}

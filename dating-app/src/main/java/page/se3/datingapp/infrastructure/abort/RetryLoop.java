package page.se3.datingapp.infrastructure.abort;

import page.se3.datingapp.core.type.Out;
import page.se3.datingapp.core.util.ThreadUtils;

public final class RetryLoop {

    private RetryLoop() {}

    @FunctionalInterface
    public interface RetryRunnable {
        void run() throws AbortException;
    }

    @FunctionalInterface
    public interface RetrySupplier<R> {
        R apply() throws AbortException;
    }

    public static boolean run(
            RetryRunnable retryRunnable,
            long periodMilliseconds,
            int retries
    ) {
        do {
            try {
                retryRunnable.run();
                return true;
            } catch (AbortException e) {
                ThreadUtils.sleep(periodMilliseconds);
            }
        } while ((retries -= 1) >= 0);

        return false;
    }

    public static <R> boolean runWithResult(
            RetrySupplier<R> retrySupplier,
            long periodMilliseconds,
            int retries,
            Out<R> result
    ) {
        do {
            try {
                result.set(retrySupplier.apply());
                return true;
            } catch (AbortException e) {
                ThreadUtils.sleep(periodMilliseconds);
            }
        } while ((retries -= 1) >= 0);

        return false;
    }
}

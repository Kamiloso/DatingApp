package page.se3.datingapp.domain.exception.retry;

import page.se3.datingapp.domain.core.util.ThreadUtils;

public final class RetryCoordinator {

    private RetryCoordinator() {}

    @FunctionalInterface
    public interface ThrowingRunnable<E extends Exception> {
        void run() throws E;
    }

    @FunctionalInterface
    public interface ThrowingSupplier<E extends Exception, R> {
        R apply() throws E;
    }

    public static <E extends Exception> void run(
            ThrowingRunnable<E> runnable,
            int retries,
            int sleepMillisInitial,
            Class<E> clazz
    ) throws E {
        apply(() -> {
            runnable.run();
            return null;
        }, retries, sleepMillisInitial, clazz);
    }

    public static <E extends Exception, R> R apply(
            ThrowingSupplier<E, R> supplier,
            int retries,
            int sleepMillisInitial,
            Class<E> clazz
    ) throws E {
        int attemptsLeft = retries;
        int sleepMillis = sleepMillisInitial;

        while (true) {
            try {
                return supplier.apply();
            } catch (Exception e) {
                if (!clazz.isInstance(e)) {
                    throw e;
                }

                if (attemptsLeft <= 0) {
                    throw new TooManyRetriesException(e, retries);
                }

                attemptsLeft--;
                ThreadUtils.sleep(sleepMillis);
                sleepMillis *= 2;
            }
        }
    }
}

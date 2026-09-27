package page.se3.datingapp.domain.core.util;

public final class ThreadUtils {

    private ThreadUtils() {}

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

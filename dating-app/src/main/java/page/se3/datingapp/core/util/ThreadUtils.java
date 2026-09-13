package page.se3.datingapp.core.util;

public final class ThreadUtils {

    private ThreadUtils() {}

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

package page.se3.datingapp.application.interfaces;

public interface DistributedLocker {

    void lockExecute(Runnable runnable, String key, long lockAtLeastForMillis, long lockAtMostForMillis);
}

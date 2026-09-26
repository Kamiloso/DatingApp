package page.se3.datingapp.domain.exception.retry;

public class TooManyRetriesException extends RuntimeException {

    public TooManyRetriesException(Throwable e, int retries) {
        super("Failed to retry " + retries + " times in a row", e);
    }
}

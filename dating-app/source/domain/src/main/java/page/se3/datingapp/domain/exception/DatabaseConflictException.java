package page.se3.datingapp.domain.exception;

public final class DatabaseConflictException extends Exception {

    public DatabaseConflictException(Throwable e) {
        super(e);
    }
}

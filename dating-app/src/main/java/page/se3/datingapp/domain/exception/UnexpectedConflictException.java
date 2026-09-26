package page.se3.datingapp.domain.exception;

public class UnexpectedConflictException extends RuntimeException {

    public UnexpectedConflictException() {}

    public UnexpectedConflictException(Throwable e) {
        super(e);
    }
}

package page.se3.datingapp.domain.exception;

public class UnexpectedRuntimeException extends RuntimeException {

    public UnexpectedRuntimeException(String message) {
        super(message);
    }

    public UnexpectedRuntimeException(Throwable e) {
        super(e);
    }
}

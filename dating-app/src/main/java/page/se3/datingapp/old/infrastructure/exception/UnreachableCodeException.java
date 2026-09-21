package page.se3.datingapp.old.infrastructure.exception;

public class UnreachableCodeException extends RuntimeException {

    public UnreachableCodeException() {}

    public UnreachableCodeException(Exception e) {
        super(e);
    }
}

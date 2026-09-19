package page.se3.datingapp.infrastructure.exception;

public class UnreachableCodeException extends RuntimeException {

    public UnreachableCodeException(Exception e) {
        super(e);
    }
}

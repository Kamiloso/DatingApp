package page.se3.datingapp.infrastructure.exception;

public class UnreachableException extends RuntimeException {

    public UnreachableException(Exception e) {
        super(e);
    }
}

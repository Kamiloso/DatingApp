package page.se3.datingapp.old.infrastructure.exception.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.old.infrastructure.exception.AbortException;

public final class MatchNotFound extends AbortException {

    public MatchNotFound() {
        super(HttpStatus.NOT_FOUND);
    }
}

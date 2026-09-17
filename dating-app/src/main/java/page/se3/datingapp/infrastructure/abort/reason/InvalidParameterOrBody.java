package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class InvalidParameterOrBody extends AbortException {

    public InvalidParameterOrBody() {
        super(HttpStatus.BAD_REQUEST);
    }
}

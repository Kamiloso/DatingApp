package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class InvalidParameterOrBody extends AbortException {

    public InvalidParameterOrBody() {
        super(HttpResponse.BAD_REQUEST);
    }
}

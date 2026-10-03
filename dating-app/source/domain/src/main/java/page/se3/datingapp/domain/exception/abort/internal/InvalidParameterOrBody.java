package page.se3.datingapp.domain.exception.abort.internal;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class InvalidParameterOrBody extends AbortException {

    public InvalidParameterOrBody() {
        super(HttpResponse.BAD_REQUEST, "Jakarta validation has failed");
    }
}

package page.se3.datingapp.domain.exception.abort.internal;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AuthorizationFailed extends AbortException {

    public AuthorizationFailed() {
        super(HttpResponse.UNAUTHORIZED, "Provided JWT token was not valid");
    }
}

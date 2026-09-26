package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AuthorizationFailed extends AbortException {

    public AuthorizationFailed() {
        super(HttpResponse.UNAUTHORIZED);
    }
}

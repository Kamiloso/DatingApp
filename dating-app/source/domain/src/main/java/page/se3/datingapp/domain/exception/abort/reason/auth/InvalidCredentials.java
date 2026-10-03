package page.se3.datingapp.domain.exception.abort.reason.auth;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class InvalidCredentials extends AbortException {

    public InvalidCredentials() {
        super(HttpResponse.NOT_FOUND, "Email not found or invalid password was provided");
    }
}

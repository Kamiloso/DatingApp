package page.se3.datingapp.domain.exception.abort.reason.auth;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class EmailValidationFailed extends AbortException {

    public EmailValidationFailed() {
        super(HttpResponse.BAD_REQUEST);
    }
}

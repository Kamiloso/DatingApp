package page.se3.datingapp.domain.exception.abort.reason.auth;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class EmailRegexMismatch extends AbortException {

    public EmailRegexMismatch() {
        super(HttpResponse.BAD_REQUEST);
    }
}

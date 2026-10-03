package page.se3.datingapp.domain.exception.abort.reason.auth;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class InvalidVerificationCode extends AbortException {

    public InvalidVerificationCode() {
        super(HttpResponse.NOT_FOUND, "Failed to identify provided verification code");
    }
}

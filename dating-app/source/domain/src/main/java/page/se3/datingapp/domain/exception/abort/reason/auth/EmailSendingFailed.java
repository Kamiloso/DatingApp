package page.se3.datingapp.domain.exception.abort.reason.auth;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class EmailSendingFailed extends AbortException {

    public EmailSendingFailed() {
        super(HttpResponse.CONFLICT);
    }
}

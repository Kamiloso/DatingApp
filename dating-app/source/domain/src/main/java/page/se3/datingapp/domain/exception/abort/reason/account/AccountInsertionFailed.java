package page.se3.datingapp.domain.exception.abort.reason.account;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AccountInsertionFailed extends AbortException {

    public AccountInsertionFailed() {
        super(HttpResponse.CONFLICT);
    }
}

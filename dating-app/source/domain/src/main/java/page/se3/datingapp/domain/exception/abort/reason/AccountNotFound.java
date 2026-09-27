package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AccountNotFound extends AbortException {

    public AccountNotFound() {
        super(HttpResponse.NOT_FOUND);
    }
}

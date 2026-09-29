package page.se3.datingapp.domain.exception.abort.reason.account;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AccountAlreadyExists extends AbortException {

    public AccountAlreadyExists() {
        super(HttpResponse.CONFLICT);
    }
}

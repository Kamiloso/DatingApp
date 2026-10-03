package page.se3.datingapp.domain.exception.abort.reason.account;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AccountNotRegistered extends AbortException {

    public AccountNotRegistered() {
        super(HttpResponse.NOT_FOUND, "No registered account referring this session was found");
    }
}

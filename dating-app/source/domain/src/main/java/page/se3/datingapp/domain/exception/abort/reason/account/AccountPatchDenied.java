package page.se3.datingapp.domain.exception.abort.reason.account;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class AccountPatchDenied extends AbortException {

    public AccountPatchDenied() {
        super(HttpResponse.FORBIDDEN);
    }
}

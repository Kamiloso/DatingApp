package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockAccessDenied extends AbortException {

    public BlockAccessDenied() {
        super(HttpResponse.FORBIDDEN);
    }
}

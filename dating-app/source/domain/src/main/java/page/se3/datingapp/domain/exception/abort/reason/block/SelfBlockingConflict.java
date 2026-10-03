package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class SelfBlockingConflict extends AbortException {

    public SelfBlockingConflict() {
        super(HttpResponse.CONFLICT);
    }
}

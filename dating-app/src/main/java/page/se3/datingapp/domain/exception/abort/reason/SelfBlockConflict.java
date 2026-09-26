package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class SelfBlockConflict extends AbortException {

    public SelfBlockConflict() {
        super(HttpResponse.CONFLICT);
    }
}

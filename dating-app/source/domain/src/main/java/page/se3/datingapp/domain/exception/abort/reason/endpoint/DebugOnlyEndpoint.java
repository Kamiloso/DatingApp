package page.se3.datingapp.domain.exception.abort.reason.endpoint;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class DebugOnlyEndpoint extends AbortException {

    public DebugOnlyEndpoint() {
        super(HttpResponse.FORBIDDEN);
    }
}

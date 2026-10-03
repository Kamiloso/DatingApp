package page.se3.datingapp.domain.exception.abort.reason.endpoint;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class DevelopmentOnlyEndpoint extends AbortException {

    public DevelopmentOnlyEndpoint() {
        super(HttpResponse.FORBIDDEN, "This endpoint cannot be accessed in production");
    }
}

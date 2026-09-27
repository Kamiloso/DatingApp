package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class DataIntegrityViolation extends AbortException {

    public DataIntegrityViolation() {
        super(HttpResponse.CONFLICT);
    }
}

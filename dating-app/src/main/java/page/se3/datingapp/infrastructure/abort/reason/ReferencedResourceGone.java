package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class ReferencedResourceGone extends AbortException {

    public ReferencedResourceGone() {
        super(HttpStatus.GONE);
    }
}

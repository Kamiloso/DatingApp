package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockAccessDenied extends AbortException {

    public BlockAccessDenied() {
        super(HttpResponse.FORBIDDEN, "No permission to access this block");
    }
}

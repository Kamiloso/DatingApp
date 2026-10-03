package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockInsertionFailed extends AbortException {

    public BlockInsertionFailed() {
        super(HttpResponse.CONFLICT);
    }
}

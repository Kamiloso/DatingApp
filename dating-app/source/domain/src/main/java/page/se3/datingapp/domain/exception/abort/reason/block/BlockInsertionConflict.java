package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockInsertionConflict extends AbortException {

    public BlockInsertionConflict() {
        super(HttpResponse.CONFLICT);
    }
}

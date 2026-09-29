package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockSelfConflict extends AbortException {

    public BlockSelfConflict() {
        super(HttpResponse.CONFLICT);
    }
}

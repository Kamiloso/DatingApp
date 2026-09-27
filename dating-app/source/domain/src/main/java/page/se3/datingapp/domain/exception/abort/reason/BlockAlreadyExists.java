package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockAlreadyExists extends AbortException {

    public BlockAlreadyExists() {
        super(HttpResponse.CONFLICT);
    }
}

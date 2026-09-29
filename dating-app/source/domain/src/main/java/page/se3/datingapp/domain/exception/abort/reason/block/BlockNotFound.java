package page.se3.datingapp.domain.exception.abort.reason.block;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class BlockNotFound extends AbortException {

    public BlockNotFound() {
        super(HttpResponse.NOT_FOUND);
    }
}

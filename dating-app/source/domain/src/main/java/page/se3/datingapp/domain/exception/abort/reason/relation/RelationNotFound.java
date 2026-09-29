package page.se3.datingapp.domain.exception.abort.reason.relation;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class RelationNotFound extends AbortException {

    public RelationNotFound() {
        super(HttpResponse.NOT_FOUND);
    }
}

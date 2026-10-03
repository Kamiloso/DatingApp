package page.se3.datingapp.domain.exception.abort.reason.message;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class MessageNotFound extends AbortException {

    public MessageNotFound() {
        super(HttpResponse.NOT_FOUND);
    }
}

package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class ChatNotFound extends AbortException {

    public ChatNotFound() {
        super(HttpResponse.NOT_FOUND);
    }
}

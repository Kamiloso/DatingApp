package page.se3.datingapp.domain.exception.abort.reason;

import page.se3.datingapp.domain.exception.abort.AbortException;

public final class ChatAccessDenied extends AbortException {

    public ChatAccessDenied() {
        super(HttpResponse.FORBIDDEN);
    }
}

package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class ChatAccessDenied extends AbortException {

    public ChatAccessDenied() {
        super(HttpStatus.FORBIDDEN);
    }
}

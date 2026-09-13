package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class ChatNotFound extends AbortException {

    public ChatNotFound() {
        super(HttpStatus.NOT_FOUND, "Chat not found");
    }
}

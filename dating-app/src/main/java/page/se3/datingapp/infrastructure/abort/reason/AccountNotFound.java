package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class AccountNotFound extends AbortException {

    public AccountNotFound() {
        super(HttpStatus.NOT_FOUND);
    }
}

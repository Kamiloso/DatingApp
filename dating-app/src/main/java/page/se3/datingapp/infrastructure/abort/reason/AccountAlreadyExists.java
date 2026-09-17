package page.se3.datingapp.infrastructure.abort.reason;

import org.springframework.http.HttpStatus;
import page.se3.datingapp.infrastructure.abort.AbortException;

public final class AccountAlreadyExists extends AbortException {

    public AccountAlreadyExists() {
        super(HttpStatus.CONFLICT);
    }
}
